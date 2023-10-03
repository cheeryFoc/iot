package com.hxk.demo.api.system;

import com.hxk.demo.module.user.entity.vo.UserVO;
import com.hxk.demo.module.system.service.ManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manage")
public class ManageResource {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ManageService service;

    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<UserVO>> getList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(service.getList(), httpHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    @GetMapping("/resetPassword/{userName}")
    public ResponseEntity<Void> resetPassword(@PathVariable("userName") String userName) {
        service.resetPassword(userName);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @GetMapping("/changeIdentity/{userName}")
    public ResponseEntity<Void> changeIdentity(@PathVariable("userName") String userName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  开始对其他用户升级 ");
        service.changeIdentity(userName);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @GetMapping("/downgrading/{userName}")
    public ResponseEntity<Void> downgrading(@PathVariable("userName") String userName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  开始对其他用户降级 ");
        service.downgrading(userName);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

}
