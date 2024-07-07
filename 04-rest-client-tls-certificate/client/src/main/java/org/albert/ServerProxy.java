package org.albert;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/server")
//@RegisterRestClient(baseUri = "http://localhost:8081")
@RegisterRestClient
public interface ServerProxy
{
    @GET
    @Path("")
    String callServer();
}
