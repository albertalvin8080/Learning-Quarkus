package org.albert.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.schedule.CounterScheduler;

@Path("/scheduler")
public class SchedulerResource
{
    @Inject
    CounterScheduler counterScheduler;

    // curl http://localhost:8080/scheduler
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCounter()
    {
        final int i = counterScheduler.get();
        return Response.ok(i).build();
    }
}
