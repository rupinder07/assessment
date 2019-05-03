package com.nagarro.internalportal;

import com.nagarro.common.RabbitMQConfiguration;
import io.dropwizard.Configuration;

class OrderServiceConfiguration extends Configuration {

    private RabbitMQConfiguration rabbitMq;

    public RabbitMQConfiguration getRabbitMq() {
        return rabbitMq;
    }

    public void setRabbitMq(final RabbitMQConfiguration rabbitMq) {
        this.rabbitMq = rabbitMq;
    }
}
