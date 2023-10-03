package com.hxk.demo.module.device.entity;

import com.hxk.demo.module.user.entity.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(name = "device")
public class Device extends AbstractAuditingEntity {

    private static final long serialVersionUID = 2266123195042023116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deviceName", columnDefinition = "varchar(40) COMMENT '设备名称uuid'", nullable = false, unique = true)
    private String deviceName;

    @Column(name = "alias", columnDefinition = "varchar(40) COMMENT '设备别名'", nullable = true)
    private String alias;

    @Column(name = "groupId", columnDefinition = "varchar(40) COMMENT '分组id'", nullable = true)
    private Long groupId;


    public Device() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }


    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", alias='" + alias + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
