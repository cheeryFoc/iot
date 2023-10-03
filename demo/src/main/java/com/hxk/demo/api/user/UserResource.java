package com.hxk.demo.api.user;

import com.hxk.demo.module.user.entity.dto.AccountDTO;
import com.hxk.demo.module.user.entity.dto.UserInfoDTO;
import com.hxk.demo.module.user.entity.dto.UserRegisterDTO;
import com.hxk.demo.module.user.entity.vo.UserVO;
import com.hxk.demo.module.user.service.AuthService;
import com.hxk.demo.module.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * UserResource
 *
 */
@RestController
@RequestMapping("/api/users")
@Api(tags = {"用户资源"})
public class UserResource {

    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/info/{userName}")
    @ApiOperation(value = "根据用户名搜索用户信息")
    public ResponseEntity<List<UserVO>> getInfo(@PathVariable String userName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在进行用户搜索 ");
        List<UserVO> userVO = userService.getUserInfoByName(userName);
        return ResponseEntity.ok(userVO);
    }

    @GetMapping("/{userName}")
    @ApiOperation(value = "根据用户名获取用户信息")
    public ResponseEntity<UserVO> getUser(@PathVariable String userName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在进行获取用户信息 ");
        UserVO userVO = userService.getUserVO(userName);
        return ResponseEntity.ok(userVO);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        userService.register(userRegister);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userName}")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "根据用户名删除用户信息")
    public ResponseEntity<Void> deleteByUserName(@PathVariable("userName") String userName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在进行删除用户信息 ");
        userService.delete(userName);
        logger.info("用户( "+ user + " )  已经完成删除用户信息 ");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/anyone/{userName}")
    @ApiOperation(value = "删除用户")
    public ResponseEntity<Void> deleteUser(@PathVariable("userName") String userName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在注销");
        userService.deleteUser(userName);
        logger.info("用户( "+ user + " )  已经注销");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updatePwd")
    @ApiOperation(value = "更新密码")
    public ResponseEntity<Void> updatePwd(@RequestBody AccountDTO accountDTO) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在更新密码");
        userService.updatePwd(accountDTO);
        logger.info("用户( "+ user + " )  已经更新密码");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateInfo")
    @ApiOperation(value = "更新个人信息")
    public ResponseEntity<Void> updateInfo(@RequestBody UserInfoDTO dto) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在更新个人信息");
        userService.updateInfo(dto);
        logger.info("用户( "+ user + " )  已经更新个人信息");
        return ResponseEntity.ok().build();
    }
}
