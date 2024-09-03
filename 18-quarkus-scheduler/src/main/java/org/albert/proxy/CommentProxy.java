package org.albert.proxy;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.albert.entity.Comment;
import org.albert.entity.Post;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
@Path("/comments")
public interface CommentProxy
{
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    List<Comment> getComment(@QueryParam("postId") Integer postId);

//    @GET
//    @Path("")
//    @Produces(MediaType.APPLICATION_JSON)
//    List<Comment> getAll();
}