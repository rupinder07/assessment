package com.nagarro.policyservice;

import com.nagarro.policyservice.handler.UnderwritingHandler;
import com.nagarro.policyservice.messaging.BaseSubscriber;
import com.nagarro.policyservice.messaging.UnderwritingEventSubscriber;
import com.nagarro.policyservice.repository.UnderwritingRepositoryImpl;
import com.nagarro.common.RabbitMqManager;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PolicyApplication extends Application<PolicyConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyApplication.class);

    public static void main(String[] args) {
        try {
            new PolicyApplication().run(args);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void run(final PolicyConfiguration configuration, final Environment environment) throws Exception {
        final BaseSubscriber subscriber = new BaseSubscriber();

        final DBIFactory factory = new DBIFactory();
        final DBI datasource = factory.build(environment, configuration.getDatabaseConfiguration(), "postgresql");

        RabbitMqManager.initializeConnection(configuration.getRabbitMq(), subscriber);
        subscriber.registerSubscriber(new UnderwritingEventSubscriber(
                new UnderwritingHandler(
                        new UnderwritingRepositoryImpl(datasource)
                )
        ));
    }

}
