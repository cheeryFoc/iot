package com.hxk.demo.module.system.entity.dto;

public class MqttDateDto {

    private String data;
    private String topic;

    public MqttDateDto() {
    }

    public MqttDateDto(String data, String topic) {
        this.data = data;
        this.topic = topic;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "MqttDateDto{" +
                "data='" + data + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }

}
