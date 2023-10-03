package com.hxk.demo.mqtt;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 发送消息接口
 *
 */
//@Component
@MessagingGateway(defaultRequestChannel = "mqttOutputChannel")
@IntegrationComponentScan
@ComponentScan
public interface MqttGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}
