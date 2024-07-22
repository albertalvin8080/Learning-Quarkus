package org.albert.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.dto.AuthorDTO;
import org.albert.entity.Author;
import org.albert.service.AuthorService;

@Path("/author")
public class AuthorResource
{
    @Inject
    AuthorService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(service.getAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(AuthorDTO authorDTO)
    {
        return service.create(authorDTO)
                .map(aDTO -> Response.status(Response.Status.CREATED).entity(aDTO).build())
                .orElse(Response.status(Response.Status.BAD_REQUEST).build());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, AuthorDTO authorDTO)
    {
        return service.update(id, authorDTO)
                .map(a -> Response.ok(a).build())
                .orElse(Response.status(Response.Status.BAD_REQUEST).build());
    }
}
