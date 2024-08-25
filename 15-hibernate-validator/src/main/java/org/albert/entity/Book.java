package org.albert.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.albert.validation.ValidationBookGroups;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public class Book
{
    private Long id;

    @NotBlank(message = "The name of the book must be present.")
    // The validation of @Length will occur only when you use:
    //     @Valid @ConvertGroup(to = ValidationBookGroups.Post.class) Book book
    @Length(min = 5, groups = ValidationBookGroups.Post.class, message = "Name min length: 5")
    private String name;

    @NotBlank(message = "The name of the author must be present.")
    @Length(max = 5, groups = ValidationBookGroups.PostForService.class, message = "Author max length: 5")
    private String author;

    @Min(value = 1, message = "Min price is 1")
    @NotNull(message = "Price must not be empty.")
    private BigDecimal price;

    public Book(Long id, String name, String author, BigDecimal price)
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public Book()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
}
