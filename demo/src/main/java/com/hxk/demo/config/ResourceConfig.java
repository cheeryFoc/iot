package com.hxk.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @projectName: demo
 * @package: com.hxk.demo.config
 * @className: resourceConfig
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {

    //仍需跨域
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/logs/**").addResourceLocations("file:./logs/");
    }

}
