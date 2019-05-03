package com.nagarro.internalportal.resources;

import com.nagarro.internalportal.UnderwritingResourceHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Response getAll(){
        final List<UnderwritingDto> underwritings = resourceHandler.getAll();
    }
}
