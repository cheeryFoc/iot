package com.hxk.demo.module.device.repository;

import com.hxk.demo.module.device.entity.DeviceGroup;
import com.hxk.demo.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

    Optional<DeviceGroup> findByGroupName(String groupName);
}
