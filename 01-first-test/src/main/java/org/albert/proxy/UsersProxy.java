package org.albert.proxy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.albert.entity.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com/")
@Path("/users")
public interface UsersProxy
{
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    User getUser(@PathParam("id") Long id);

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    List<User> getAll();
}
