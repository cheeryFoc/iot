package com.hxk.demo.module.system.repository;

import com.hxk.demo.module.system.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Transactional
    @Modifying
    void deleteByPlatform(String platform);

}
