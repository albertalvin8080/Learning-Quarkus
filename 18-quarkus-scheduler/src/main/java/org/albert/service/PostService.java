package org.albert.service;

import io.quarkus.scheduler.Scheduled;
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

    // This will put comments inside a Post which doesn't have any comment yet.
    // Yeah, it's really stupid, but it works.
    @Scheduled(cron = "1 */1 * * * ?")
    void fetchComments()
    {
        if(postList == null) return;
        postList.stream()
                .filter(p -> p.getComment() == null)
                .forEach(p -> {
                    final List<Comment> comments = commentProxy.getComments(p.getId());
                    p.setComment(comments);
                });
    }
}
