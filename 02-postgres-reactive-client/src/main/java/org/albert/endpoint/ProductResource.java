package org.albert.endpoint;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Author;
import org.albert.service.ProductService;

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
                    if(author != null)
                        return Response.ok(author);
                    else
                        return Response.status(Response.Status.NOT_FOUND);
                })
                .onItem()
                // Same as `Response.ok(author).build()`.
                .transform(Response.ResponseBuilder::build);
    }
}
