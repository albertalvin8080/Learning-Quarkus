package org.albert;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.repository.BookRepository;

@Path("/book")
public class BookResource
{
    @Inject
    BookRepository bookRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(bookRepository.listAll()).build();
    }
}
