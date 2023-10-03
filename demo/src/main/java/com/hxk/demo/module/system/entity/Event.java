package com.hxk.demo.module.system.entity;

import com.hxk.demo.module.user.entity.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.entity
 * @className: Event
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */

@Entity
@Table(name = "event")
public class Event extends AbstractAuditingEntity {

    private static final long serialVersionUID = 3340873364530753417L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", columnDefinition="varchar(30)")
    private String eventName;

    @Column(name = "device_name", columnDefinition = "varchar(40) COMMENT '设备名称uuid'")
    private String deviceName;

    @Column(name = "message", columnDefinition = "varchar(255)")
    private String message;

    @Column(name = "user_name", columnDefinition = "varchar(40)")
    private String userName;

    public Event() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", message='" + message + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
