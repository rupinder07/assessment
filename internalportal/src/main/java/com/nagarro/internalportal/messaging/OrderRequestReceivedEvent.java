package com.nagarro.internalportal.messaging;

public class OrderRequestReceivedEvent implements JsonContent {

    private final String key = "msg.command.order.create";
    private final OrderDto orderDto;

    public OrderRequestReceivedEvent(final OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public String getKey() {
        return key;
    }
}
