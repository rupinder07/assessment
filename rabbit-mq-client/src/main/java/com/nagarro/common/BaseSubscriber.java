package com.nagarro.common;

import com.google.common.collect.Lists;

import java.util.List;

public class BaseSubscriber implements MessageSubscriber {

    private final List<ServiceSubscriber> subscribers = Lists.newArrayList();

    public void registerSubscriber(final ServiceSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    public void onMessage(final BaseMessage message) {
        subscribers.stream()
                .filter(subscriber -> subscriber.supports(message.getRoutingKey()))
                .forEach(subscriber -> subscriber.handleMessage(message));
    }


}
