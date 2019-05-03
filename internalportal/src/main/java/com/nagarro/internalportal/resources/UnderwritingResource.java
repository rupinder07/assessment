package com.nagarro.internalportal.resources;

import com.nagarro.internalportal.MessagingException;
import com.nagarro.internalportal.UnderwritingResourceHandler;
import com.nagarro.internalportal.dto.UnderwritingDto;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/underwriting")
public class UnderwritingResource {

    private final UnderwritingResourceHandler resourceHandler;

    public UnderwritingResource(final UnderwritingResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(resourceHandler.getAll()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") String id,
                           final UnderwritingDto dto) {
        try {
            resourceHandler.update(dto);
            return Response.noContent().build();
        } catch (MessagingException e) {
            return Response.status(HttpStatus.BAD_REQUEST_400)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
