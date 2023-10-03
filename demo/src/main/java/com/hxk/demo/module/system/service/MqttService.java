package com.hxk.demo.module.system.service;

import com.hxk.demo.module.system.entity.Record;
import com.hxk.demo.utils.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.service
 * @className: MqttService
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Service
public class MqttService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisCache redisCache;

    public Map<String, String> getData(String tag) {
        Map<String, String> map = new HashMap<>();
        if (redisCache.hasKey(tag)) {
            map = redisCache.getCacheMap(tag);
        }
        return map;
    }
}
