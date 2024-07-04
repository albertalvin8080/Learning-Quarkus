package org.albert.endpoint;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Path("/product")
public class ProductEndpoint
{
    private static List<Product> productList = new ArrayList<>(List.of(
            new Product(1L, "TV"),
            new Product(2L, "GPU"),
            new Product(3L, "CPU")
    ));

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(productList).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    // Because the product is being passed in the body,
    // you don't need to annotate the parameter of this method.
    public Response save(Product product)
    {
        productList.add(product);
        return Response.ok(productList).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id)
    {
        return productList.remove(new Product(id, null)) ?
                Response.noContent().build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(
            @PathParam("id") Long id,
            @QueryParam("newName") String newName
    )
    {
        Product oldProduct = new Product(id, null);
        int index = productList.indexOf(oldProduct);

        boolean updated = false;
        if (index != -1)
        {
            productList.set(index, new Product(id, newName));
            updated = true;
        }

        return updated ?
                Response.ok(productList).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }
}
