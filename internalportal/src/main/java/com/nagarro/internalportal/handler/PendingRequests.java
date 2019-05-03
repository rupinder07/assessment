package com.nagarro.internalportal.handler;

import com.nagarro.common.messaging.BaseMessage;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class PendingRequests {

    private final Map<String, AsyncResponseHolder> requests = new ConcurrentHashMap();

    AsyncResponseHolder createResponseHolder(BaseMessage request) {
        final String correlationId = (String) request.getHeaders().get("CORRELATION_ID");
        AsyncResponseHolder responseHolder = new AsyncResponseHolder(correlationId, () -> {
            this.finish(correlationId);
        });
        AsyncResponseHolder currentHolder = (AsyncResponseHolder) this.requests.putIfAbsent(responseHolder.correlationId(), responseHolder);
        if (currentHolder != null) {
            throw new IllegalStateException("Pending requests already tracks " + correlationId);
        } else {
            return responseHolder;
        }
    }

    private void finish(String correlationId) {
        if (correlationId != null) {
            this.requests.remove(correlationId);
        }

    }

    Optional<AsyncResponseHolder> findAndRemoveHolder(String correlationId) {
        return Optional.ofNullable(this.requests.remove(correlationId));
    }
}
