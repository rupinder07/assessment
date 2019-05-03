package com.nagarro.common;

public interface ServiceSubscriber {

    default boolean supports(final String routingKey) {
        return false;
    }

    void handleMessage(final BaseMessage message);
}
