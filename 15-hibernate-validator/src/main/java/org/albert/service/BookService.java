package org.albert.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import org.albert.entity.Book;
import org.albert.validation.ValidationBookGroups;

@ApplicationScoped
public class BookService
{
    public void validate(@Valid Book book)
    {
    }

    public void validateWithGroup(
            @Valid
            // See annotation inside Book.class
            @ConvertGroup(to = ValidationBookGroups.PostForService.class)
            Book book
    )
    {

    }
}
