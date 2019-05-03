package com.nagarro.common;

import com.rabbitmq.client.LongString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class BaseMessage {

    private static final String CORRELATION_ID = "CORRELATION_ID";
    private static final String TIMESTAMP = "TIMESTAMP";

    private final String body;
    private final String routingKey;
    private Map<String, Object> headers = new HashMap<>();

    public BaseMessage(final String body, final String routingKey) {
        this.body = body;
        this.routingKey = routingKey;
        headers.put(CORRELATION_ID, UUID.randomUUID().toString());
        headers.put(TIMESTAMP, LocalDateTime.now().toString());
    }

    public String getBody() {
        return body;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, Object> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return String.format("Body: %s \t Routing Key: %s \t Headers: %s", body, routingKey, headers);
    }

    public String getCorrelationId() {
        return Objects.nonNull(headers)
                ? Objects.nonNull(headers.get(CORRELATION_ID))
                ? ((LongString) headers.get(CORRELATION_ID)).toString()
                : ""
                : "";
    }

    public String getTimestamp() {
        return Objects.nonNull(headers)
                ? Objects.nonNull(headers.get(TIMESTAMP))
                ? ((LongString) headers.get(TIMESTAMP)).toString()
                : ""
                : "";
    }
}
