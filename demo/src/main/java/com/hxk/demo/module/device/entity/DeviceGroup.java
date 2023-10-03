package com.hxk.demo.module.device.entity;

import com.hxk.demo.module.user.entity.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(name = "device_group")
public class DeviceGroup extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1166123195042023116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupName", columnDefinition = "varchar(40) COMMENT '分组名'", nullable = true , unique = true)
    private String groupName;

    public DeviceGroup() {
    }

    public DeviceGroup(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "DeviceGrouping{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
