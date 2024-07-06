package org.albert.endpoint;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.User;
import org.albert.proxy.UsersProxy;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/users")
public class UsersEndpoint
{
    @RestClient
    private UsersProxy usersProxy;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id)
    {
        final User user = usersProxy.getUser(id);
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        final List<User> userList = usersProxy.getAll();
        return Response.ok().entity(userList).build();
    }
}
