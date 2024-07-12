package org.albert.resource;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.proxy.UsersProxy;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/users")
public class UsersResource
{
    // It throws and exception if the property cannot be found.
//    @ConfigProperty(name = "app.id")
    Long idProperty;

    @PostConstruct
    void init()
    {
        ConfigProvider.getConfig().getOptionalValue("app.id", Long.class)
                .ifPresent(id -> idProperty = id);
    }

    @RestClient
    UsersProxy usersProxy;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return usersProxy.getAll();
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@QueryParam("id") Long id)
    {
        if(id == null)
            id = idProperty;
        return usersProxy.getById(id);
    }
}
