package com.nagarro.policyservice.messaging;

import com.nagarro.policyservice.handler.UnderwritingHandler;
import com.nagarro.policyservice.messaging.dto.UnderwritingMessageDto;
import com.nagarro.common.BaseMessage;

import java.io.IOException;

public class UnderwritingEventSubscriber implements ServiceSubscriber {

    private static final String UPDATE_UNDERWRITING = "msg.event.underwriting.update";

    private final UnderwritingHandler handler;

    public UnderwritingEventSubscriber(final UnderwritingHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean supports(final String routingKey) {
        return UPDATE_UNDERWRITING.equalsIgnoreCase(routingKey);
    }

    @Override
    public void handleMessage(final BaseMessage message) {
        try {
            if (message.getRoutingKey().equalsIgnoreCase(UPDATE_UNDERWRITING)) {
                handler.update(UnderwritingMessageDto.fromJson(message.getBody()), message.getHeaders());
            }
        } catch (IOException e) {
            // do nothing
        }
    }
}
