package com.hxk.demo.config.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mqtt")
public class MqttProperties {
    private String username;
    private String password;
    private String[] hostUrl;
    private String clientId;
    private String defaultTopic;
    private Long connectionTimeout;
    private String[] subscriptionTopic;

    public MqttProperties() {
    }

    public MqttProperties(String username, String password, String[] hostUrl, String clientId, String defaultTopic, Long connectionTimeout, String[] subscriptionTopic) {
        this.username = username;
        this.password = password;
        this.hostUrl = hostUrl;
        this.clientId = clientId;
        this.defaultTopic = defaultTopic;
        this.connectionTimeout = connectionTimeout;
        this.subscriptionTopic = subscriptionTopic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String[] hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String[] getSubscriptionTopic() {
        return subscriptionTopic;
    }

    public void setSubscriptionTopic(String[] subscriptionTopic) {
        this.subscriptionTopic = subscriptionTopic;
    }

}
