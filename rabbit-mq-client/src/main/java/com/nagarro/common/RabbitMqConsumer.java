package com.nagarro.common;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RabbitMqConsumer implements Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConsumer.class);

    private final MessageSubscriber subscriber;

    public RabbitMqConsumer(final MessageSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void handleConsumeOk(final String s) {

    }

    @Override
    public void handleCancelOk(final String s) {

    }

    @Override
    public void handleCancel(final String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(final String s, final ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(final String s) {

    }

    @Override
    public void handleDelivery(final String s,
                               final Envelope envelope,
                               final AMQP.BasicProperties basicProperties,
                               final byte[] bytes) throws IOException {
        final BaseMessage baseMessage = new BaseMessage(new String(bytes), envelope.getRoutingKey());
        baseMessage.setHeaders(basicProperties.getHeaders());
        LOGGER.info(String.format("Received message: %s", baseMessage.toString()));
        subscriber.onMessage(baseMessage);
    }
}
