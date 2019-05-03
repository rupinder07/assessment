package com.nagarro.policyservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nagarro.common.RabbitMQConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

class PolicyConfiguration extends Configuration {

    @JsonProperty("database")
    private DataSourceFactory databaseConfiguration;

    private RabbitMQConfiguration rabbitMq;

    public DataSourceFactory getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    public void setDatabaseConfiguration(final DataSourceFactory databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    public RabbitMQConfiguration getRabbitMq() {
        return rabbitMq;
    }

    public void setRabbitMq(final RabbitMQConfiguration rabbitMq) {
        this.rabbitMq = rabbitMq;
    }
}
