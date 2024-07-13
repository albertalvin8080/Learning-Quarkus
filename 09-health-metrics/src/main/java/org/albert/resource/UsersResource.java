package org.albert.resource;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.proxy.UsersProxy;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.concurrent.TimeUnit;

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

    // http://localhost:8080/q/metrics/application
    @GET
    @Counted(name = "countedGetAll",
            description = "The number of times the getAll method is called.")
    @Timed(name = "timedGetAll",
        description = "How long it takes for getAll method to be executed.")
    @Metered(name = "meteredGetAll",
        description = "Tracks the frequency of getAll method invocations.")
    @Gauge(name = "gaugeGetAll",
            description = "Tracks a specific value for the getAll method.",
            unit = MetricUnits.MILLISECONDS)
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
