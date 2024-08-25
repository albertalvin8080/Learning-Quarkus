package org.albert.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Path("/book")
public class BookResource
{
    private final List<Book> books = new ArrayList<>(List.of(
            new Book(1L, "Elden Ring", BigDecimal.valueOf(100.50)),
            new Book(2L, "Dark Souls 3", BigDecimal.valueOf(90.0))
    ));

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id)
    {
        return books.stream().filter(b -> b.getId().equals(id))
                .findFirst()
                .map(b -> Response.ok(b).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
