package org.albert.healthcheck;

import org.albert.proxy.UsersProxy;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Liveness checks determine if the application is alive and functioning properly.
@Liveness
public class UsersHealthCheck implements HealthCheck
{
    @RestClient
    UsersProxy usersProxy;

    @Override
    public HealthCheckResponse call()
    {
        // If the proxy returns an exception, the health check is going to be DOWN.
        usersProxy.getAll();
        return HealthCheckResponse.named("Users Endpoint").up().build();
    }
}
