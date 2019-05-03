FROM ubuntu:14.0

ENV ROOT_DIRECTORY /etc/nagp/order-service

COPY target/inventory-service.jar /var/lib/nagp/order-service/service.jar
COPY config.yml ${ROOT_DIRECTORY}/config.yml

EXPOSE 8010

WORKDIR ${ROOT_DIRECTORY}

VOLUME ${ROOT_DIRECTORY}
VOLUME /var/lib/nagp/order-service

ENTRYPOINT ["java", "-Dfile.encoding=utf-8", "-jar", "/var/lib/nagp/order-service/service.jar"]

CMD ["server", "config.yml"]
