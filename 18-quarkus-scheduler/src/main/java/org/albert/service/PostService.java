package org.albert.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.albert.entity.Comment;
import org.albert.entity.Post;
import org.albert.proxy.CommentProxy;
import org.albert.proxy.PostProxy;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PostService
{
    @RestClient
    PostProxy postProxy;
    @RestClient
    CommentProxy commentProxy;

    private List<Post> postList;

    public List<Post> getAll()
    {
        if(postList == null)
           postList = new ArrayList<>(postProxy.getAll());

        return postList;
    }
}
