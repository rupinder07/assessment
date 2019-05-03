package com.nagarro.common.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nagarro.common.RabbitMQConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class BaseConfiguration extends Configuration {

    private DataSourceFactory database;

    private RabbitMQConfiguration rabbitMq;

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(final DataSourceFactory database) {
        this.database = database;
    }

    public RabbitMQConfiguration getRabbitMq() {
        return rabbitMq;
    }

    public void setRabbitMq(final RabbitMQConfiguration rabbitMq) {
        this.rabbitMq = rabbitMq;
    }
}
