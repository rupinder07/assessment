package com.nagarro.common;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMqManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqManager.class);

    private static final Map<String, Object> NONE = null;

    static Channel channel;
    private static String exchangeName;

    public static void initializeConnection(final RabbitMQConfiguration configuration, final MessageSubscriber subscriber)
            throws IOException, TimeoutException, MessagingException {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(configuration.getHost());
        connectionFactory.setPort(configuration.getPort());
        connectionFactory.setUsername(configuration.getUsername());
        connectionFactory.setPassword(configuration.getPassword());

        final Connection connection = connectionFactory.newConnection();
        RabbitMqManager.channel = connection.createChannel();
        RabbitMqManager.exchangeName = configuration.getExchangeName();
        channel.exchangeDeclare(RabbitMqManager.exchangeName, "direct", true);
        for (String topic : configuration.getTopicNames()) {
            declareAndSubscribeQueues(topic, configuration);
        }
        initializeSubscriber(configuration, subscriber);
    }

    public static void publishMessage(final String routingKey, final String message, final Map<String, Object> headers)
            throws IOException {
        LOGGER.info(String.format("Published message: %s with routing key: %s", message, routingKey));
        channel.basicPublish(RabbitMqManager.exchangeName,
                routingKey,
                new AMQP.BasicProperties.Builder()
                        .headers(headers)
                        .build(),
                message.getBytes());
    }

    private static void initializeSubscriber(final RabbitMQConfiguration config, final MessageSubscriber subscriber)
            throws IOException {
        for (final String exchangeTopicName : config.getTopicNames()) {
            if (!exchangeTopicName.equalsIgnoreCase("")) {
                channel.basicConsume(exchangeTopicName, new RabbitMqConsumer(subscriber));
            }
        }
    }

    private static void declareAndSubscribeQueues(String topicName, final RabbitMQConfiguration configuration)
            throws MessagingException {
        try {
            channel.queueDeclare(topicName, false, false, false, NONE);

            if (configuration.getMessageSubscriptions() != null
                    && configuration.getMessageSubscriptions().get(topicName) != null
                    && configuration.getMessageSubscriptions().get(topicName).size() > 0) {
                configuration.getMessageSubscriptions().get(topicName)
                        .forEach(subscription -> {
                                    try {
                                        channel.queueBind(topicName, configuration.getExchangeName(), subscription);
                                    } catch (IOException e) {
                                        //do nothing
                                    }
                                }
                        );
            } else {
                channel.queueBind(topicName, configuration.getExchangeName(), topicName);
            }
        } catch (IOException e) {
            throw new MessagingException(e.getMessage());
        }

    }


}
