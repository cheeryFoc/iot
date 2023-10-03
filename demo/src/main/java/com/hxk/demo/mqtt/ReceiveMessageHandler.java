package com.hxk.demo.mqtt;

import com.alibaba.fastjson2.JSON;
import com.hxk.demo.exception.ServiceException;
import com.hxk.demo.module.system.entity.Event;
import com.hxk.demo.module.system.entity.Info;
import com.hxk.demo.module.system.entity.Record;
import com.hxk.demo.module.system.entity.dto.EventDTO;
import com.hxk.demo.module.system.repository.EventRepository;
import com.hxk.demo.module.system.repository.copy.EventCopy;
import com.hxk.demo.utils.redis.RedisCache;
import com.hxk.demo.websocket.WebSocketProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 接收消息处理器
 *
 */
public class ReceiveMessageHandler implements MessageHandler {

    private Logger logger = LoggerFactory.getLogger("sys-date");

    @Resource
    private RedisCache redisCache;
    @Resource
    private EventRepository eventRepository;
    @Resource
    private WebSocketProcess webSocketProcess;
    @Resource
    private EventCopy copy;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();

        String msg = message.getPayload().toString();


        if ("info".equals(topic) || "warn".equals(topic) || "error".equals(topic)) {
            if ("warn".equals(topic) || "error".equals(topic)) {
                EventDTO dto = JSON.parseObject(msg, EventDTO.class);
                Event event = copy.convertOfEventDTO(dto);
                eventRepository.save(event);
            }
            try {
                webSocketProcess.sendAllMessage(msg);
            } catch (Exception e) {
                throw new ServiceException("fail to send message", e);
            }
        } else if ("record".equals(topic)) {
//            logger.info(topic + msg);
            Record record = JSON.parseObject(msg, Record.class);
            if (!ObjectUtils.isEmpty(record)) {
                Map<String, String> map = new HashMap<>();
                if (redisCache.hasKey(record.getId())) {
                    map = redisCache.getCacheMap(record.getId());
                }
                map.put(record.getAttribute(), msg);
                redisCache.setCacheMap(record.getId(), map);
            }
        } else {
            logger.info(topic + msg);
            try {
                webSocketProcess.sendAllMessage(msg);
                Info info = JSON.parseObject(msg, Info.class);
                if (!ObjectUtils.isEmpty(info) && info.getAttribute() != null) {
                    Map<String, String> map = new HashMap<>();
                    if (redisCache.hasKey(topic)) {
                        map = redisCache.getCacheMap(topic);
                    }
                    map.put(info.getAttribute(), msg);
                    redisCache.setCacheMap(topic, map);
                }
            } catch (Exception e) {
                throw new ServiceException("fail to send data", e);
            }
        }

    }

}
