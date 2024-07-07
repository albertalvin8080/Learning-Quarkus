package org.albert;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hello")
public class AppOneResource
{
    @RestClient
    AppTwoProxy appTwoProxy;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Fallback(fallbackMethod = "helloFallback")
    @Timeout(2000L) // Comment and uncomment this to test the circuit breaker taking control of the requests.
    @CircuitBreaker(
            delay = 4000, // The delay after which an open circuit will transition to half-open state.
            failureRatio = 0.5, // The ratio of failures within the rolling window that will trip the circuit to open.
            requestVolumeThreshold = 4, // The number of consecutive requests in a rolling window.
            successThreshold = 2 // The number of successful executions, before a half-open circuit is closed again.
    )
    public String message(@QueryParam("m") String message)
    {
        return "App 1 and " + appTwoProxy.hello() + ": " + message;
    }

    // Must have the same signature parameters as the original method.
    private String helloFallback(String message)
    {
        return "App 1 falling back";
    }
}
