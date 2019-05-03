package com.nagarro.internalportal.handler;

import com.nagarro.common.RabbitMqManager;
import com.nagarro.common.messaging.BaseMessage;
import com.nagarro.internalportal.MessagingException;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestHandler {
    private static final long TIMEOUT = Long.parseLong(System.getProperty("async.request.timeout", "10000"));
    private final PendingRequests pendingRequests;

    public RequestHandler() {
        this.pendingRequests = new PendingRequests();
    }

    public BaseMessage callWithTimeout(BaseMessage request) throws MessagingException {
        try {
            return this.publishCommandWithTimeout(request, TIMEOUT);
        } catch (IOException | InterruptedException | TimeoutException | ExecutionException e) {
            throw new MessagingException(e.getMessage());
        }
    }

    private BaseMessage publishCommandWithTimeout(BaseMessage request, long timeOutInMillis)
            throws IOException, InterruptedException, ExecutionException, TimeoutException {
        AsyncResponseHolder responseHolder = null;
        BaseMessage message;
        try {
            responseHolder = this.pendingRequests.createResponseHolder(request);
            RabbitMqManager.publishMessage(request.getRoutingKey(), request.getBody(), request.getHeaders());
            message = responseHolder.response(timeOutInMillis, TimeUnit.MILLISECONDS);
        } finally {
            if (responseHolder != null) {
                responseHolder.onDone();
            }
        }
        return message;
    }

    public Optional<AsyncResponseHolder> get(String correlationId) {
        return this.pendingRequests.findAndRemoveHolder(correlationId);
    }
}