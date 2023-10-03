package com.hxk.demo.module.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.entity.vo
 * @className: EventVO
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class EventVO {

    private Long id;

    private String eventName;

    private String deviceName;

    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDate;
    private String userName;

    public EventVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
