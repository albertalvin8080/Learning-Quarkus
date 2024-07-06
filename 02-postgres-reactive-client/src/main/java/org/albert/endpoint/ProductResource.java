package org.albert.endpoint;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Author;
import org.albert.service.ProductService;

import java.net.URI;

@Path("/product")
public class ProductResource
{
    @Inject
    ProductService productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Author> getAll()
    {
        return productService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findById(@PathParam("id") Long id)
    {
        return productService.findById(id)
                .onItem()
                .transform(author -> {
                    // If author is null, it means that no one was found inside the db.
                    if (author != null)
                        return Response.ok(author);
                    else
                        return Response.status(Response.Status.NOT_FOUND);
                })
                .onItem()
                // Same as `Response.ok(author).build()`.
                .transform(Response.ResponseBuilder::build);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> save(Author author)
    {
        return productService.save(author)
                .onItem()
                .transform(id -> {
                    final URI uri = URI.create("/product/" + id);
                    // This returns a header named `Location` which contains the URL.
                    return Response.created(uri).build();
                });
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id)
    {
        return productService.delete(id)
                .onItem()
                .transform(b -> b ?
                        Response.noContent().build() :
                        Response.status(Response.Status.NOT_FOUND).build()
                );
    }

    @PUT
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> update(Author author)
    {
        return productService.update(author)
                .onItem()
                .transform(b -> b ?
                        Response.noContent().build() :
                        Response.status(Response.Status.NOT_FOUND).build()
                );
    }
}
