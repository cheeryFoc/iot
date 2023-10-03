package com.hxk.demo.config;

import com.hxk.demo.module.system.entity.Subscription;
import com.hxk.demo.module.system.service.SubscriptionService;
import com.hxk.demo.module.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import java.util.List;

@Configuration
public class DataInitConfig implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemService systemService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter adapter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Config implements ApplicationRunner");
        logger.info("start init data from SQL");

//        先订阅，再处理身份
        logger.info("处理订阅");
        List<Subscription> list = subscriptionService.getList();
        if (list != null && list.size() > 0) {
            for (Subscription subscription : list) {
                adapter.addTopic(subscription.getTopic());
                logger.info("add " + subscription.getTopic());
            }
        }

        logger.info("处理中.....");
        systemService.initRole("root");
        systemService.initRole("xixixi");


        logger.info("complete");
    }

}
