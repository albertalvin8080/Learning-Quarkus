package org.albert.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.jwt.JwtService;

@Path("/jwt")
@ApplicationScoped
public class JwtResource
{
    @Inject
    JwtService jwtService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJwt()
    {
        return Response.ok(jwtService.getJwt()).build();
    }
}
