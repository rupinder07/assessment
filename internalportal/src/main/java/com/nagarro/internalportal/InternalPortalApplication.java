package com.nagarro.internalportal;

import com.nagarro.common.BaseSubscriber;
import com.nagarro.common.RabbitMqManager;
import com.nagarro.common.dropwizard.BaseConfiguration;
import com.nagarro.internalportal.handler.RequestHandler;
import com.nagarro.internalportal.handler.UnderwritingHandler;
import com.nagarro.internalportal.messaging.UnderwritingEventSubscriber;
import com.nagarro.internalportal.repository.UnderwritingRepositoryImpl;
import com.nagarro.internalportal.resources.UnderwritingResource;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class InternalPortalApplication extends Application<BaseConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalPortalApplication.class);

    public static void main(String[] args) {
        try {
            new InternalPortalApplication().run(args);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void initialize(final Bootstrap<BaseConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<BaseConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(BaseConfiguration configuration) {
                return configuration.getDatabase();
            }
        });
        bootstrap.addBundle(new TemplateConfigBundle());
    }

    public void run(final BaseConfiguration configuration, final Environment environment) throws Exception {
        final RequestHandler requestHandler = new RequestHandler();
        final DBIFactory factory = new DBIFactory();
        final DBI datasource = factory.build(environment, configuration.getDatabase(), "postgresql");
        registerSubscribers(configuration, requestHandler);
        registerResources(environment, datasource, requestHandler);
    }

    private void registerSubscribers(final BaseConfiguration configuration, final RequestHandler requestHandler)
            throws IOException, TimeoutException, MessagingException {
        final BaseSubscriber subscriber = new BaseSubscriber();
        RabbitMqManager.initializeConnection(configuration.getRabbitMq(), subscriber);
        subscriber.registerSubscriber(new UnderwritingEventSubscriber(requestHandler::get));
    }

    private void registerResources(final Environment environment,
                                   final DBI datasource,
                                   final RequestHandler requestHandler) {
        environment.jersey()
                .register(
                        new UnderwritingResource(
                                new UnderwritingResourceHandler(
                                        new UnderwritingHandler(
                                                new UnderwritingRepositoryImpl(datasource)
                                        ),
                                        requestHandler
                                )
                        )
                );
    }
}
