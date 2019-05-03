package com.nagarro.internalportal;

import com.nagarro.internalportal.messaging.InventoryEventSubscriber;
import com.nagarro.internalportal.handler.OrderHandler;
import com.nagarro.internalportal.handler.RequestHandler;
import com.nagarro.internalportal.messaging.BaseSubscriber;
import com.nagarro.internalportal.resources.UnderwritingResource;
import com.nagarro.common.RabbitMqManager;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InternalPortalApplication extends Application<OrderServiceConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalPortalApplication.class);

    public static void main(String[] args) {
        try {
            new InternalPortalApplication().run(args);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void run(final OrderServiceConfiguration configuration, final Environment environment) throws Exception {
        final RequestHandler requestHandler = new RequestHandler();
        final BaseSubscriber subscriber = new BaseSubscriber();
        RabbitMqManager.initializeConnection(configuration.getRabbitMq(), subscriber);
        subscriber.registerSubscriber(new InventoryEventSubscriber(requestHandler::get));
        environment.jersey().register(new UnderwritingResource(new OrderHandler(requestHandler)));
    }
}
