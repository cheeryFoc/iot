package com.hxk.demo.api.system;

import com.hxk.demo.module.system.entity.vo.EventVO;
import com.hxk.demo.module.system.service.EventService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: demo
 * @package: com.hxk.demo.api.system
 * @className: NotionResource
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/notion")
@Api(tags = "Notion")
public class EventResource {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private EventService service;

    @GetMapping("/list")
    public ResponseEntity<List<EventVO>> getList() {
        List<EventVO> list = service.getList();
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/list/{group}")
    public ResponseEntity<List<EventVO>> findEventByGroup(@PathVariable("group") String group) {
        List<EventVO> list = service.findEventByGroup(group);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/operate/{id}")
    public ResponseEntity<Void> operateById(@PathVariable("id") String id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在确认处理事件完成 ");
        service.operateById(id, user);
        logger.info("用户( "+ user + " )  已经确认处理事件完成 ");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在删除事件");
        service.deleteById(id);
        logger.info("用户( "+ user + " )  已经删除事件");
        return ResponseEntity.ok().build();
    }
}
