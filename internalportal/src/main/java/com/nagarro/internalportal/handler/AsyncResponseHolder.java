package com.nagarro.internalportal.handler;

import com.nagarro.nagp.rabbitmqclient.BaseMessage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncResponseHolder {
    private final String correlationId;
    private final Runnable onDone;
    private final CompletableFuture<BaseMessage> future = new CompletableFuture();

    public AsyncResponseHolder(String  correlationId, Runnable onDone) {
        this.correlationId = correlationId;
        this.onDone = onDone;
    }

    public void assignResponse(BaseMessage response) {
        this.future.complete(response);
    }

    public void assignError(Throwable t) {
        this.future.completeExceptionally(t);
    }

    public BaseMessage response(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return (BaseMessage) this.future.get(timeout, unit);

    }

    public String correlationId() {
        return this.correlationId;
    }

    public void onDone() {
        if (this.onDone != null) {
            this.onDone.run();
        }

    }
}
