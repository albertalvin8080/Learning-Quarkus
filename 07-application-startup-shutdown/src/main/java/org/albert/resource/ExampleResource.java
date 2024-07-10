package org.albert.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.domain.Author;
import org.albert.repository.AuthorRepository;

import java.util.List;

@Path("/author")
public class ExampleResource
{
    @Inject
    AuthorRepository authorRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        final List<Author> authors = authorRepository.listAll();
        return Response.ok(authors).build();
    }
}
