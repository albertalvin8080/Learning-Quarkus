package org.albert.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Product;
import org.albert.service.ProductService;

import java.net.URI;
import java.util.Optional;

@Path("/product")
public class ProductResource
{
    @Inject
    ProductService productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(productService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id)
    {
        return productService.findByIdOptional(id)
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Product product)
    {
        return productService.save(product) ?
                Response.created(URI.create("/product/" + product.id)).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@PathParam("name") String name)
    {
        return productService.findByNameOptional(name)
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id)
    {
        return productService.delete(id) ?
                Response.noContent().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Product dtoProduct)
    {
        return productService.updateProduct(id, dtoProduct)
            .map(product -> Response.ok(product).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


}
