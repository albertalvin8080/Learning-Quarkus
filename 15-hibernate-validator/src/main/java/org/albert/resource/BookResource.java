package org.albert.resource;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Book;
import org.albert.service.BookService;
import org.albert.validation.ValidationBookGroups;

import java.util.Set;
import java.util.stream.Collectors;

@Path("/book")
public class BookResource
{
    @Inject
    Validator validator;

    @POST
    @Path("/validator")
    @Consumes(MediaType.APPLICATION_JSON)
    // The Validator throws an exception if the Book object is null (basically if the body is empty).
    // Use @NotNull for that purpose.
    public Response saveValidator(@jakarta.validation.constraints.NotNull Book book)
    {
        final Set<ConstraintViolation<Book>> constraintViolationSet = validator.validate(book);
        if (!constraintViolationSet.isEmpty())
        {
            final String collected = constraintViolationSet.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST).entity(collected).build();
        }

        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @POST
    @Path("/valid")
    @Consumes(MediaType.APPLICATION_JSON)
    // @Valid doesn't validate if the Book object is null (basically if the body is empty).
    // Use @NotNull for that purpose.
    public Response saveValid(@NotNull @Valid Book book)
    {
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @POST
    @Path("/validWithGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveValidWithGroup(
            @NotNull
            @Valid
            // See annotation inside Book.class
            @ConvertGroup(to = ValidationBookGroups.Post.class)
            Book book)
    {
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @Inject
    BookService bookService;

    @POST
    @Path("/service")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveValidateService(@NotNull Book book)
    {
        bookService.validate(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @POST
    @Path("/serviceWithGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveValidateServiceWithGroup(@NotNull Book book)
    {
        bookService.validateWithGroup(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }
}
