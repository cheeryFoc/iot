package com.hxk.demo.module.device.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_model",indexes = {
        @Index(columnList="deviceId")
})
public class DeviceModel implements Serializable {

    private static final long serialVersionUID = 3366123195042023116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deviceId", columnDefinition = "bigint(20) COMMENT '设备id'", nullable = false)
    private Long deviceId;

    @Column(name = "attribute", columnDefinition = "varchar(40) COMMENT '设备属性'", nullable = false)
    private String attribute;

    @Column(name = "alias", columnDefinition = "varchar(40) COMMENT '属性别名'", nullable = true)
    private String alias;

    @Column(name = "io", columnDefinition = "varchar(5) COMMENT '0滑动写入,1可读,2开关指令'", nullable = false)
    private String io;

    @Column(name = "max_value", columnDefinition = "int DEFAULT '100' COMMENT '最大值'", nullable = true)
    private Long maxValue;

    @Column(name = "min_value", columnDefinition = "int DEFAULT '0' COMMENT '最小值'", nullable = true)
    private Long minValue;

    @Column(name = "scale", columnDefinition = "varchar(10) DEFAULT '' COMMENT '数据单位'", nullable = true)
    private String scale;

    @Column(name = "first_command", columnDefinition = "varchar(40) COMMENT '默认指令'", nullable = true)
    private String firstCommand;

    @Column(name = "second_command", columnDefinition = "varchar(40) COMMENT '指令二'", nullable = true)
    private String secondCommand;


    public DeviceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getIo() {
        return io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }


    public String getFirstCommand() {
        return firstCommand;
    }

    public void setFirstCommand(String firstCommand) {
        this.firstCommand = firstCommand;
    }

    public String getSecondCommand() {
        return secondCommand;
    }

    public void setSecondCommand(String secondCommand) {
        this.secondCommand = secondCommand;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", attribute='" + attribute + '\'' +
                ", alias='" + alias + '\'' +
                ", io='" + io + '\'' +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", scale='" + scale + '\'' +
                ", firstCommand='" + firstCommand + '\'' +
                ", secondCommand='" + secondCommand + '\'' +
                '}';
    }
}
