package org.albert;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.proxy.UsersProxy;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/users")
public class UsersResource
{
    @RestClient
    UsersProxy usersProxy;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return usersProxy.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id)
    {
        return usersProxy.getById(id);
    }
}
