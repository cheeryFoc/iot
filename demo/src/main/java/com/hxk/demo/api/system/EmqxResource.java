package com.hxk.demo.api.system;

import com.hxk.demo.module.system.service.EmqxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: demo
 * @package: com.hxk.demo.api.system
 * @className: EmqxResource
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/emqx")
public class EmqxResource {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EmqxService service;

    @DeleteMapping ("/offline/{deviceName}")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> offline(@PathVariable("deviceName") String deviceName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  开始执行设备下线 ");
        return service.offline(deviceName);
    }


}
