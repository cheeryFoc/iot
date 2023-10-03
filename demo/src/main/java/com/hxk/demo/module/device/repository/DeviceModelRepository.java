package com.hxk.demo.module.device.repository;

import com.hxk.demo.module.device.entity.DeviceModel;
import com.hxk.demo.module.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {

    List<DeviceModel> findByDeviceId(Long deviceId);

    @Transactional
    @Modifying
    void deleteByDeviceId(Long deviceId);
}
