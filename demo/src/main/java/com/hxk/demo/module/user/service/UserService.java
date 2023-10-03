package com.hxk.demo.module.user.service;

import com.hxk.demo.constant.UserRoleConstants;
import com.hxk.demo.exception.AlreadyExistsException;
import com.hxk.demo.exception.ServiceException;
import com.hxk.demo.exception.common.CodeEnum;
import com.hxk.demo.exception.common.CommonException;
import com.hxk.demo.module.system.jdbc.ManageJDBC;
import com.hxk.demo.module.user.entity.User;
import com.hxk.demo.module.user.entity.UserRole;
import com.hxk.demo.module.user.entity.dto.AccountDTO;
import com.hxk.demo.module.user.entity.dto.UserInfoDTO;
import com.hxk.demo.module.user.entity.dto.UserRegisterDTO;
import com.hxk.demo.module.user.entity.vo.UserVO;
import com.hxk.demo.module.user.repository.UserRepository;
import com.hxk.demo.module.user.repository.copy.UserCopy;
import com.hxk.demo.websocket.WebSocketProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserCopy userCopy;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ManageJDBC manageJDBC;
    @Resource
    private WebSocketProcess webSocketProcess;

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 注册
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterDTO dto) {
        // 预检查用户名是否存在
        Optional<User> userOptional = this.getUserByName(dto.getUserName());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("Save failed, the user name already exist.");
        }
        User user = userCopy.convertOfUserRegisterDTO(dto);
        // 将登录密码进行加密
        String cryptPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        user.setPassword(cryptPassword);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // 如果预检查没有检查到重复，就利用数据库的完整性检查
            throw new AlreadyExistsException("Save failed, the user name already exist.2");
        }
        UserRole userRole = new UserRole();
        userRole.setUserName(dto.getUserName());
        userRole.setRole(UserRoleConstants.ROLE_USER);
        userRoleService.setRole(userRole);
    }

    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public UserVO getUserVO(String userName) {
        Optional<User> userOptional = this.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with user name: " + userName);
        }
        return manageJDBC.getUserVO(userName);
    }

    public List<UserVO> getUserInfoByName(String userName) {
        List<UserVO> list = manageJDBC.getInfoByName(userName + "%");
        if (list.isEmpty()) {
            throw new ServiceException(CodeEnum.NOT_SUCCEED.getMsg(), new CommonException(CodeEnum.NOT_SUCCEED));
        }
        int[] arr = new int[list.size()];
        int index = 0;
        for (int i = 1; i < list.size(); i++) {
            UserVO curr = list.get(index);
            UserVO next = list.get(i);
            if (curr.getId().equals(next.getId())) {
                List<String> roles = curr.getRoles();
                roles.addAll(next.getRoles());
                curr.setRoles(roles);
                list.set(index,curr);
                arr[i] = 1;
            } else {
                index = i;
            }
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 1) {
                list.remove(i);
            }
        }
        return list;
    }

    public List<String> listUserRoles(String userName) {
        return userRoleService.listUserRoles(userName)
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String userName) {
        this.isUser(userName);

        Optional<User> userOptional = this.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        List<String> roles = this.listUserRoles(userName);
        if (roles.contains("ROLE_ROOT")) {
            throw new ServiceException(CodeEnum.NOT_SUPPORT_DEL.getMsg(), new CommonException(CodeEnum.NOT_SUPPORT_DEL));
        }
        userRoleService.deleteByUserName(userName);
        userRepository.deleteByUserName(userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String userName) {
        this.isUser(userName);

        Optional<User> userOptional = this.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        User user = userOptional.get();
        List<String> roles = this.listUserRoles(userName);
        String optName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> curRoles = this.listUserRoles(optName);

        if (!curRoles.contains("ROLE_ROOT") && (roles.contains("ROLE_ROOT") || roles.contains("ROLE_ADMIN"))) {
            throw new ServiceException(CodeEnum.NOT_SUPPORT_DEL.getMsg(), new CommonException(CodeEnum.NOT_SUPPORT_DEL));
        }

        // 删除用户角色信息
        userRoleService.deleteByUserName(userName);
        // 删除用户基本信息
        userRepository.deleteByUserName(userName);
        // 下线
        try {
            webSocketProcess.sendMessage(user.getId(), "{\"eventName\":\"exit\"}");
        } catch (Exception e) {
            throw new ServiceException("fail to outline", e);
        }
    }

    public void isUser(String userName) {
        if ("root".equals(userName) || "xixixi".equals(userName)) {
            throw new ServiceException(CodeEnum.NOT_SUPPORT_DEL.getMsg(), new CommonException(CodeEnum.NOT_SUPPORT_DEL));
        }
    }

    public void updatePwd(AccountDTO accountDTO) {
        String userName = accountDTO.getUserName();
        String password = accountDTO.getPassword();

        // 根据用户名获取用户信息
        Optional<User> userOptional = this.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        User user = userOptional.get();

        // 验证登录密码是否正确。如果正确，则赋予用户相应权限并生成用户认证信息
        if (this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            log.info("密码正确");
            String cryptPassword = bCryptPasswordEncoder.encode(accountDTO.getNewPassword());
            user.setPassword(cryptPassword);
            userRepository.save(user);
        } else {
            throw new ServiceException(CodeEnum.PWD_MATCHING_ERROR.getMsg(), new CommonException(CodeEnum.PWD_MATCHING_ERROR));
        }
    }

    /**
     * 重置密码
     * @param userName
     */
    public void resetPassword(String userName) {
        Optional<User> userOptional = this.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        User user = userOptional.get();
        String cryptPassword = bCryptPasswordEncoder.encode("123456");
        user.setPassword(cryptPassword);
        userRepository.save(user);
    }

    public void updateInfo(UserInfoDTO dto) {
        String userName = dto.getUserName();
        Optional<User> userOptional = this.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        User user = userOptional.get();
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setNickName(dto.getNickName());
        userRepository.save(user);
    }

}
