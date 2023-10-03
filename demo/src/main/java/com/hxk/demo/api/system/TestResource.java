package com.hxk.demo.api.system;

import com.hxk.demo.constant.SecurityConstants;
import com.hxk.demo.utils.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: demo
 * @package: com.hxk.demo.api.system
 * @className: TestResource
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/test")
public class TestResource {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisCache redisCache;


    @GetMapping("/hello")
    public ResponseEntity<Void> hello() {
        String url = "http://localhost:8081/api/v4/clients/mqttx_cc351865";
        RestTemplate restTemplate = new RestTemplate();
        String token = "Basic YWRtaW46cHVibGlj";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        System.out.println(response);
        return ResponseEntity.ok().build();
    }

}
