package com.hxk.demo.module.device.entity.vo;


/**
 * @projectName: demo
 * @package: com.hxk.demo.module.device.entity.vo
 * @className: DeviceVo
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class DeviceVo {

    private Long id;
    private String deviceName;
    private String alias;
    private String groupName;
    private String status;
    private String lastModifiedDate ;
    private String createDate;


    public DeviceVo() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "DeviceVo{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", alias='" + alias + '\'' +
                ", groupName='" + groupName + '\'' +
                ", status='" + status + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", createDate=" + createDate +
                '}';
    }
}
