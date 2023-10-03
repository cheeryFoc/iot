package com.hxk.demo.api.device;

import com.hxk.demo.module.device.entity.DeviceGroup;
import com.hxk.demo.module.device.entity.DeviceModel;
import com.hxk.demo.module.device.entity.dto.DeviceDto;
import com.hxk.demo.module.device.entity.dto.InfoDTO;
import com.hxk.demo.module.device.entity.vo.DeviceVo;
import com.hxk.demo.module.device.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @projectName: demo
 * @package: com.hxk.demo.api.device
 * @className: DeviceResource
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */

@RestController
@RequestMapping("/api/devices")
public class DeviceResource {

    @Autowired
    private DeviceService service;
    Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/add")
    @ApiOperation(value = "添加设备")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> add(@RequestBody @Valid DeviceDto dto) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在添加设备 ");
        Map<String, String> map = service.add(dto);
        logger.info("用户( "+ user + " )  已经完成添加设备 ");
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(map, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/list")
    @ApiOperation(value = "设备列表")
    public ResponseEntity<List<DeviceVo>> getList() {
        List<DeviceVo> list = service.getList();
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/model/{deviceId}")
    public ResponseEntity<List<DeviceModel>> getModel(@PathVariable("deviceId") String deviceId) {
        List<DeviceModel> list = service.getModel(deviceId);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/group")
    public ResponseEntity<List<DeviceGroup>> getGroup() {
        List<DeviceGroup> list = service.getGroup();
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/list/{group}")
    public ResponseEntity<List<DeviceVo>> getDeviceByGroup(@PathVariable("group") String group) {
        List<DeviceVo> list = service.getDeviceByGroup(group);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{deviceName}")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "根据设备名删除设备信息")
    public ResponseEntity<Void> deleteByDeviceName(@PathVariable("deviceName") String deviceName) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在删除设备信息 ");
        service.delete(deviceName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ROOT') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "编辑设备信息")
    public ResponseEntity<Void> edit(@RequestBody InfoDTO dto) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在编辑设备信息 ");
        service.edit(dto);
        return ResponseEntity.ok().build();
    }

}
