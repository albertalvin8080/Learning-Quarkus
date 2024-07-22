package org.albert.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Author;
import org.albert.repository.AuthorRepository;

@Path("/author")
public class AuthorResource
{
    @Inject
    AuthorRepository authorRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(authorRepository.listAll()).build();
    }

    @GET
    @Path("/{firstName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByFirstName(@PathParam("firstName") String firstName)
    {
        return Response.ok(authorRepository.findByFirstName(firstName)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Author author)
    {
        authorRepository.persist(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }
}
