package com.nagarro.common;

public interface MessageSubscriber {

    void onMessage(final BaseMessage message);
}
