package com.hxk.demo.api.system;

import com.hxk.demo.module.system.entity.Record;
import com.hxk.demo.module.system.entity.dto.MqttDateDto;
import com.hxk.demo.module.system.service.MqttService;
import com.hxk.demo.mqtt.MqttGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/api/mqtt")
public class MqttResource {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MqttGateway mqttGateway;
    @Autowired
    private MqttService mqttService;

    /**
     * 发送mqtt消息
     *
     * @param dateDto
     * @return
     */
    @PostMapping("/publishTopic")
    public ResponseEntity<Void> sendMsg(@RequestBody MqttDateDto dateDto) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("用户( "+ user + " )  正在进行获取用户信息 ");
        logger.info("用户( "+ user + " )  发送消息: " + dateDto.toString());
        mqttGateway.sendToMqtt(dateDto.getData(), dateDto.getTopic());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/init/{tag}")
    public ResponseEntity<Map<String, String>> getData(@PathVariable String tag) {
        Map<String, String> record = mqttService.getData(tag);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(record, httpHeaders, HttpStatus.OK);
    }
}
