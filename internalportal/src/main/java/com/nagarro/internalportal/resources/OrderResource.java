package com.nagarro.internalportal.resources;


import com.google.gson.Gson;
import com.nagarro.internalportal.MessagingException;
import com.nagarro.internalportal.handler.OrderHandler;
import com.nagarro.nagp.rabbitmqclient.BaseMessage;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/order")
public class OrderResource {

    private final OrderHandler handler;

    public OrderResource(final OrderHandler handler) {
        this.handler = handler;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createOrder(final OrderDto orderDto) {
        try {
            final BaseMessage message = handler.createOrder(orderDto);
            final OrderDto dto = new Gson().fromJson(message.getBody(), OrderDto.class);
            return Response.created(URI.create("order")).build();
        } catch (MessagingException e) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(e.getMessage()).build();
        }
    }

}
