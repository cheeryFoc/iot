package com.hxk.demo.config;

/**
 * @projectName: demo
 * @package: com.hxk.demo.config
 * @className: WebsocketConfiguration
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebsocketConfiguration {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

