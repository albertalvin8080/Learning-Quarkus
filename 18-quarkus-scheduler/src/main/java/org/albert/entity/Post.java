package org.albert.entity;

import java.util.List;

public class Post
{
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
    private List<Comment> comment;

    public List<Comment> getComment()
    {
        return comment;
    }

    public void setComment(List<Comment> comment)
    {
        this.comment = comment;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
}
