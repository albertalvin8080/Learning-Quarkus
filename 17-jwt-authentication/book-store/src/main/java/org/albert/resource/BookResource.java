package org.albert.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Book;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import java.util.ArrayList;
import java.util.List;

// http://localhost:8081/q/swagger-ui/
@SecurityScheme(
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
@Path("/book")
@ApplicationScoped
public class BookResource
{
    private final List<Book> bookList = new ArrayList<>();

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(bookList).build();
    }

    @POST
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Book book)
    {
        bookList.add(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response delete(@PathParam("id") Integer id)
    {
        final Book book = new Book();
        book.setId(id);
        bookList.remove(book);
        return Response.noContent().build();
    }
}
