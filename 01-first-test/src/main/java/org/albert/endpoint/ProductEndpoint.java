package org.albert.endpoint;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/product")
public class ProductEndpoint
{
    private static List<String> productList = new ArrayList<>(List.of("TV", "PC", "GPU"));

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAll()
    {
        return Response.ok(productList).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    // Because the product name is being passed in the body,
    // you don't need to annotate String product
    public Response save(String product)
    {
        productList.add(product);
        return Response.ok(productList).build();
    }

    @DELETE
    @Path("/{productName}")
    public Response delete(@PathParam("productName") String productName)
    {
        return productList.remove(productName) ?
                Response.noContent().build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{oldName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(
            @PathParam("oldName") String oldName,
            @QueryParam("newName") String newName
    )
    {
        boolean rm = productList.remove(oldName);
        // Always true according to documentation, so it's not necessary to add it to the ternary.
        boolean add = productList.add(newName);

        return rm ?
                Response.ok(productList).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }
}
