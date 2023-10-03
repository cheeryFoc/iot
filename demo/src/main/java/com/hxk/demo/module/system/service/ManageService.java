package com.hxk.demo.module.system.service;

import com.hxk.demo.constant.UserRoleConstants;
import com.hxk.demo.exception.ServiceException;
import com.hxk.demo.exception.common.CodeEnum;
import com.hxk.demo.exception.common.CommonException;
import com.hxk.demo.module.user.entity.User;
import com.hxk.demo.module.user.entity.UserRole;
import com.hxk.demo.module.user.entity.vo.UserVO;
import com.hxk.demo.module.system.jdbc.ManageJDBC;
import com.hxk.demo.module.user.service.UserRoleService;
import com.hxk.demo.module.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManageService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ManageJDBC manageJDBC;
    @Autowired
    private UserService service;
    @Autowired
    private UserRoleService userRoleService;

    public List<UserVO> getList() {
        List<UserVO> list = manageJDBC.getList();

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
//                roles.addAll(nexRoles);//不可读写的List的原因？
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

    public void resetPassword(String userName) {
        service.resetPassword(userName);
    }

    public String changeIdentity(String userName) {
        Optional<User> userOptional = service.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        List<UserRole> list = userRoleService.listUserRoles(userName);
        if (list == null || list.size() == 0) {
            UserRole userRole = new UserRole();
            userRole.setUserName(userName);
            userRole.setRole("ROLE_ADMIN");
            userRoleService.setRole(userRole);
            logger.info("已经完成对用户 " + userName + " 升级 ");
            return CodeEnum.OPT_SUCCEED.getMsg();
        } else {
            for (UserRole userRole : list) {
                if ("".equals(userRole.getRole()) || UserRoleConstants.ROLE_USER.equals(userRole.getRole())) {
                    userRole.setRole("ROLE_ADMIN");
                    userRoleService.setRole(userRole);
                    logger.info("已经完成对用户 " + userName + " 升级 ");
                    return CodeEnum.OPT_SUCCEED.getMsg();
                } else if ("ROLE_ADMIN".equals(userRole.getRole()) && "ROLE_ROOT".equals(userRole.getRole())) {
                    return CodeEnum.NOT_SUCCEED.getMsg();
                }
            }
        }
        UserRole target = new UserRole();
        target.setUserName(userName);
        target.setRole("ROLE_ADMIN");
        userRoleService.setRole(target);
        logger.info("已经完成对用户 " + userName + " 升级 ");
        return CodeEnum.OPT_SUCCEED.getMsg();

    }

    public void downgrading(String userName) {
        Optional<User> userOptional = service.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        List<UserRole> list = userRoleService.listUserRoles(userName);
        if (list == null || list.size() == 0) {
            throw new ServiceException(CodeEnum.NOT_SUPPORT_DOWNGRADE.getMsg(), new CommonException(CodeEnum.NOT_SUPPORT_DOWNGRADE));
        } else {
            for (UserRole userRole : list) {
                if ("ROLE_ADMIN".equals(userRole.getRole())) {
                    userRole.setRole(UserRoleConstants.ROLE_USER);
                    userRoleService.setRole(userRole);
                    logger.info("已经完成对用户 " + userName + " 降级 ");
                }
            }
        }
    }
}
