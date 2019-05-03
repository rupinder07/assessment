package com.nagarro.internalportal.messaging;

import com.nagarro.common.BaseMessage;

public interface ServiceSubscriber {

    default boolean supports(final String routingKey) {
        return false;
    }

    void handleMessage(final BaseMessage message);
}
