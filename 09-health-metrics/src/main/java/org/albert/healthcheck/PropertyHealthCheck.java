package org.albert.healthcheck;

import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

// Readiness checks determine if the application is ready to handle requests.
@Readiness
public class PropertyHealthCheck implements HealthCheck
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

    @Override
    public HealthCheckResponse call()
    {
        return idProperty != null ?
                HealthCheckResponse.up("Property app.id") :
                HealthCheckResponse.down("Property app.id");
    }
}
