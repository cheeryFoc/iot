package com.hxk.demo.module.device.entity.dto;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.device.entity.dto
 * @className: InfoDTO
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class InfoDTO {

    private Long id;
    private String deviceName;
    private String alias;
    private String groupName;

    public InfoDTO() {
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "InfoDTO{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", alias='" + alias + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
