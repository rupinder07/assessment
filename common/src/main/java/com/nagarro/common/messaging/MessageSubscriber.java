package com.nagarro.common.messaging;

import com.nagarro.common.messaging.BaseMessage;

public interface MessageSubscriber {

    void onMessage(final BaseMessage message);
}
