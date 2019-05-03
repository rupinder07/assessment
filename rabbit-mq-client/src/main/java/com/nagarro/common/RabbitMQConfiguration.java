package com.nagarro.common;

import java.util.List;
import java.util.Map;

public class RabbitMQConfiguration {

    private String host;
    private int port;
    private String username;
    private String password;
    private String exchangeName;
    private Map<String, List<String>> messageSubscriptions;
    private List<String> topicNames;

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(final String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public Map<String, List<String>> getMessageSubscriptions() {
        return this.messageSubscriptions;
    }

    public void setMessageSubscriptions(final Map<String, List<String>> messageSubscriptions) {
        this.messageSubscriptions = messageSubscriptions;
    }

    public List<String> getTopicNames() {
        return topicNames;
    }

    public void setTopicNames(final List<String> topicNames) {
        this.topicNames = topicNames;
    }
}
