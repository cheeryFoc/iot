package com.hxk.demo.module.user.service;

import com.hxk.demo.constant.UserRoleConstants;
import com.hxk.demo.module.user.entity.User;
import com.hxk.demo.module.user.entity.dto.UserDTO;
import com.hxk.demo.module.user.entity.dto.UserLoginDTO;
import com.hxk.demo.security.JwtUser;
import com.hxk.demo.utils.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 用户认证服务
 *
 * @author star
 */
@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 用户登录认证
     *
     * @param userLogin 用户登录信息
     */
    public JwtUser authLogin(UserLoginDTO userLogin) {
        String userName = userLogin.getUserName();
        String password = userLogin.getPassword();

        // 根据登录名获取用户信息
        Optional<User> userOptional = userService.getUserByName(userName);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        User user = userOptional.get();

        // 验证登录密码是否正确。如果正确，则赋予用户相应权限并生成用户认证信息
        // 用BCryptPasswordEncoder 做加密，在判断时要用该对象的matches方法，第一个参数为明文！！！，第二个参数才是密文 ！！！
        if (this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            log.info("密码正确");
            List<String> roles = userService.listUserRoles(userName);
            // 如果用户角色为空，则默认赋予 ROLE_USER 角色
            if (CollectionUtils.isEmpty(roles)) {
                log.info("赋以角色");
                roles = Collections.singletonList(UserRoleConstants.ROLE_USER);
            }

            // 生成 token
            String token = JwtUtils.generateToken(userName, roles, userLogin.getRememberMe());

            // 认证成功后，设置认证信息到 Spring Security 上下文中
            Authentication authentication = JwtUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 用户信息
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(userName);
            userDTO.setEmail(user.getEmail());
            userDTO.setRoles(roles);
            return new JwtUser(token, userDTO);

        }
        throw new BadCredentialsException("The user name or password error.");
    }

    /**
     * 用户退出登录
     *
     * <p>
     * 清除 Spring Security 上下文中的认证信息
     */
    public void logout() {
        //使用了ThreadLocal机制来保存每个使用者的安全上下文。
        // 这意味着，只要针对某个使用者的逻辑执行都是在同一个线程中进行，
        // 即使不在各个方法之间以参数的形式传递其安全上下文，
        // 各个方法也能通过SecurityContextHolder工具获取到该安全上下文
        SecurityContextHolder.clearContext();
    }
}
