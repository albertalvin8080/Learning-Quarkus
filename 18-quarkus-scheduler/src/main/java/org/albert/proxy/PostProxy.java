package org.albert.proxy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.albert.entity.Post;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
@Path("/posts")
public interface PostProxy
{
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Post getPost(@PathParam("id") Long id);

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    List<Post> getAll();
}