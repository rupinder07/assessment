package com.nagarro.internalportal.messaging;

import com.nagarro.internalportal.handler.AsyncResponseHolder;
import com.nagarro.nagp.rabbitmqclient.BaseMessage;
import com.nagarro.nagp.rabbitmqclient.RabbitMqManager;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

public class InventoryEventSubscriber implements ServiceSubscriber {

    private static final String INVENTORY_AVAILABLE = "msg.event.inventory.available";
    private static final String INVENTORY_UNAVAILABLE = "msg.event.inventory.unavailable";

    private final Function<String, Optional<AsyncResponseHolder>> pendingRequests;

    public InventoryEventSubscriber(final Function<String, Optional<AsyncResponseHolder>> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    @Override
    public boolean supports(final String routingKey) {
        return INVENTORY_AVAILABLE.equalsIgnoreCase(routingKey) ||
                INVENTORY_UNAVAILABLE.equalsIgnoreCase(routingKey);
    }

    @Override
    public void handleMessage(final BaseMessage message) {
        final String correlationId = message.getCorrelationId();
        try{
            if(message.getRoutingKey().equalsIgnoreCase(INVENTORY_AVAILABLE)){
                RabbitMqManager.publishMessage("msg.command.shipping.create",
                        message.getBody(),
                        message.getHeaders());
                assignResponse(correlationId, message);
            } else{
                RabbitMqManager.publishMessage("msg.event.product.unavailable", message.getBody(), message.getHeaders());
                assignError(correlationId, message);
            }
        } catch (IOException e){
            // do nothing
        }
        assignResponse(correlationId, message);
    }

    private void assignResponse(final String correlationId,
                                  final BaseMessage response) {
        if (null != correlationId) {
            final Optional<AsyncResponseHolder> async = pendingRequests.apply(correlationId);
            if (async.isPresent()) {
                async.ifPresent((AsyncResponseHolder asyncResponseHolder) ->
                        asyncResponseHolder.assignResponse(response));
            }
        }
    }

    private void assignError(final String correlationId,
                                final BaseMessage response) {
        if (null != correlationId) {
            final Optional<AsyncResponseHolder> async = pendingRequests.apply(correlationId);
            if (async.isPresent()) {
                async.ifPresent((AsyncResponseHolder asyncResponseHolder) ->
                        asyncResponseHolder.assignError(new UnsupportedOperationException("Inventory Unavailable")));
            }
        }
    }
}
