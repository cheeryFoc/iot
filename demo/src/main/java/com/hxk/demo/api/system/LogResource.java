package com.hxk.demo.api.system;

import com.hxk.demo.exception.ServiceException;
import com.hxk.demo.module.system.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @projectName: demo
 * @package: com.hxk.demo.api.system
 * @className: LogResource
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/log")
@Api(tags = "Log")
public class LogResource {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LogService service;

    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<String>> getList() {
        List<String> list = service.getList();
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{fileName}")
    @ApiOperation(value = "删除文件")
    public ResponseEntity<Void> deleteFile(@PathVariable("fileName") String fileName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在删除日志 " + fileName);
        service.deleteFile(fileName);
        logger.info("用户( "+ user + " )  已经删除日志 " + fileName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Void> download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  开始下载日志 " + fileName);
//        service.fileDownLoad(fileName, response);
        File file = new File("./logs/" + fileName);
        if(!file.exists()){
            throw new ServiceException("文件不存在", new FileExistsException());
        }
//        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            throw new ServiceException("文件下载失败", e);
        }
        logger.info("用户( "+ user + " )  已经下载日志 " + fileName);
        return ResponseEntity.ok().build();
    }
}
