package com.hxk.demo.module.system.entity;

import com.hxk.demo.module.user.entity.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(name = "subscription")
public class Subscription extends AbstractAuditingEntity {

    private static final long serialVersionUID = 2986123195042023116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic", columnDefinition = "varchar(40) COMMENT '订阅'", nullable = false)
    private String topic;

    @Column(name = "platform", columnDefinition = "varchar(40) COMMENT '订阅平台'", nullable = false)
    private String platform;

    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
