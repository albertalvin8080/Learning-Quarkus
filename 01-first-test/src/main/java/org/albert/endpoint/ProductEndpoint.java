package org.albert.endpoint;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Product;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

/*
 * http://localhost:8080/q/openapi    — Open API Schema document
 * http://localhost:8080/q/swagger-ui — Open API UI
 * */
@Tag(name = "Product APIs")
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
    @Operation(
            operationId = "getAll",
            description = "Get all products",
            summary = "Get all products inside the productList."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Operation successful",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)
            )
    })
    public Response getAll()
    {
        return Response.ok(productList).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "save",
            description = "Saves a product.",
            summary = "Saves a product inside the productList."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Product saved.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)
            )
    })
    // Because the product is being passed in the body,
    // you don't need to annotate the parameter of this method.
    public Response save(
            @RequestBody(
                    required = true,
                    description = "Product object",
                    content = @Content(schema = @Schema(implementation = Product.class))
            )
            Product product
    )
    {
        productList.add(product);
        return Response.status(Response.Status.CREATED).entity(productList).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            operationId = "delete",
            description = "Deletes a product.",
            summary = "Deletes a product based on its id."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "Product deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Product not found."
            )
    })
    public Response delete(
            @Parameter(
                    required = true,
                    description = "Product id",
                    in = ParameterIn.PATH,
                    schema = @Schema(type = SchemaType.INTEGER, format = "int64")
            )
            @PathParam("id") Long id)
    {
        return productList.remove(new Product(id, null)) ?
                Response.noContent().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "update",
            description = "Updates a product.",
            summary = "Updates a product based on its id."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "Product updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Product not found."
            )
    })
    public Response update(
            @Parameter(
                    required = true,
                    description = "Product id",
                    in = ParameterIn.PATH,
                    schema = @Schema(type = SchemaType.INTEGER, format = "int64")
            )
            @PathParam("id") Long id,
            @Parameter(
                    required = true,
                    description = "Updated name for the product.",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = SchemaType.STRING)
            )
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
                Response.noContent().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }
}
