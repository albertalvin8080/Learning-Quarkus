package org.albert;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.TimeUnit;

@Path("/hello")
public class AppTwoResource
{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello()
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(1000);
//            TimeUnit.MILLISECONDS.sleep(5000); // Comment and uncomment this to test the circuit breaker taking control of the requests.
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return "App 2";
    }
}
