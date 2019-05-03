package com.nagarro.internalportal.handler;

import com.nagarro.internalportal.MessagingException;
import com.nagarro.internalportal.messaging.OrderRequestReceivedEvent;
import com.nagarro.nagp.rabbitmqclient.BaseMessage;

import java.util.UUID;

public class OrderHandler {

    private final RequestHandler requestHandler;

    public OrderHandler(final RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public BaseMessage createOrder(final OrderDto orderDto) throws MessagingException {
        orderDto.setId(UUID.randomUUID().toString());
        final OrderRequestReceivedEvent event = new OrderRequestReceivedEvent(orderDto);
        return requestHandler.callWithTimeout(new BaseMessage(event.toJson(), event.getKey()));
    }
}
