package com.hxk.demo.api.system;

import com.hxk.demo.constant.SecurityConstants;
import com.hxk.demo.module.user.entity.dto.UserDTO;
import com.hxk.demo.module.user.entity.dto.UserLoginDTO;
import com.hxk.demo.module.user.service.AuthService;
import com.hxk.demo.security.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "Auth")
public class AuthResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录认证")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO userLogin) {
        // 用户登录认证
        JwtUser jwtUser = authService.authLogin(userLogin);
        // 认证成功后，将 token 存入响应头中返回
        HttpHeaders httpHeaders = new HttpHeaders();
        // 添加 token 前缀 "Bearer "
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());
        logger.info("用户: " + userLogin.getUserName() + " 登录成功");
        return new ResponseEntity<>(jwtUser.getUser(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录")
    public ResponseEntity<Void> logout() {
        String optName = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户: " + optName + " 开始退出");
        authService.logout();
        logger.info("用户: " + optName + " 退出成功");
        return ResponseEntity.ok().build();
    }
}
