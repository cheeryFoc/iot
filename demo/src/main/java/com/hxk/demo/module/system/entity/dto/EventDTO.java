package com.hxk.demo.module.system.entity.dto;

import javax.persistence.Column;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.entity.dto
 * @className: EventDTO
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class EventDTO {

    private String eventName;
    private String deviceName;
    private String message;

    public EventDTO() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "eventName='" + eventName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
