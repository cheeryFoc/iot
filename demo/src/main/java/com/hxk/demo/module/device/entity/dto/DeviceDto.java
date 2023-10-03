package com.hxk.demo.module.device.entity.dto;

import com.hxk.demo.module.device.entity.DeviceModel;

import javax.persistence.Column;
import java.util.List;

public class DeviceDto {

    private String alias;
    private String groupName;

    private List<DeviceModel> modelList;

    public DeviceDto() {
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

    public List<DeviceModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<DeviceModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "alias='" + alias + '\'' +
                ", groupName='" + groupName + '\'' +
                ", modelList=" + modelList +
                '}';
    }
}
