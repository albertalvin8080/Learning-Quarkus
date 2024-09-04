package org.albert.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.albert.entity.Comment;
import org.albert.proxy.CommentProxy;
import org.albert.service.PostService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/posts")
public class PostResource
{
    @Inject
    PostService postService;
    @RestClient
    CommentProxy commentProxy;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        return Response.ok(postService.getAll()).build();
    }

    @GET
    @Path("/comment/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComment(@PathParam("postId") Integer postId)
    {
        return commentProxy.getComments(postId);
    }
}
