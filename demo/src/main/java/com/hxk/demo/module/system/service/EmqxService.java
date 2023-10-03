package com.hxk.demo.module.system.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hxk.demo.constant.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.service
 * @className: EmqxService
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Service
public class EmqxService {

    Logger logger = LoggerFactory.getLogger(getClass());

    public ResponseEntity<String> offline(String deviceName) {
        String url = "http://43.139.8.56:18083/api/v4/clients/" + deviceName;
        RestTemplate restTemplate = new RestTemplate();
        String token =  "Basic YWRtaW46dHgyMmVtcXg=";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        if (response.getStatusCodeValue() == 200) {
            logger.info("已对 " + deviceName + " 执行下线");
        }
        return response;
    }

    public List<String> status() {
        String url = "http://43.139.8.56:18083/api/v4/clients/" ;
        RestTemplate restTemplate = new RestTemplate();
        String token =  "Basic YWRtaW46dHgyMmVtcXg=";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        JSONObject obj = JSONObject.parseObject(response.getBody());
        JSONArray jsonArray = obj.getJSONArray("data");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject tmp = jsonArray.getJSONObject(i);
            String client = tmp.getString("clientid");
            logger.info(client);
            list.add(client);
        }
        return list;
    }

}
