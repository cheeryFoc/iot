package com.hxk.demo.module.device.repository;

import com.hxk.demo.module.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository  extends JpaRepository<Device, Long> {

    Optional<Device> findByDeviceName(String deviceName);

    List<Device> findByGroupId(Long groupId);

    @Modifying
    @Transactional
    void deleteByDeviceName(String deviceName);
}
