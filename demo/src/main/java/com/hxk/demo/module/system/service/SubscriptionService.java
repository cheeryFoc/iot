package com.hxk.demo.module.system.service;

import com.hxk.demo.module.system.entity.Subscription;
import com.hxk.demo.module.system.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter adapter;

    public void addSubscription(Subscription subscription){
        subscriptionRepository.save(subscription);
        adapter.addTopic(subscription.getTopic());
    }

    public void deleteByPlatform(String platform) {
        subscriptionRepository.deleteByPlatform(platform);
    }

    public List<Subscription> getList() {
        return subscriptionRepository.findAll();
    }
}
