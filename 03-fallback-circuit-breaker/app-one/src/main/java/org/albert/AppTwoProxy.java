package org.albert;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/hello")
@RegisterRestClient(baseUri = "http://localhost:8081")
public interface AppTwoProxy
{
    @GET
    @Path("")
    String hello();
}
