package com.nagarro.internalportal.messaging;

import com.nagarro.common.BaseMessage;
import com.nagarro.common.ServiceSubscriber;
import com.nagarro.internalportal.handler.AsyncResponseHolder;

import java.util.Optional;
import java.util.function.Function;

public class UnderwritingEventSubscriber implements ServiceSubscriber {

    private static final String UNDERWRITING_UPDATED = "msg.event.underwriting.updated";

    private final Function<String, Optional<AsyncResponseHolder>> pendingRequests;

    public UnderwritingEventSubscriber(final Function<String, Optional<AsyncResponseHolder>> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    @Override
    public boolean supports(final String routingKey) {
        return UNDERWRITING_UPDATED.equalsIgnoreCase(routingKey);
    }

    @Override
    public void handleMessage(final BaseMessage message) {
        final String correlationId = message.getCorrelationId();
        if (message.getRoutingKey().equalsIgnoreCase(UNDERWRITING_UPDATED)) {
            //update the local copy
            assignResponse(correlationId, message);
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
