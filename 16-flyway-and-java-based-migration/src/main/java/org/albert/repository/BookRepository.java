package org.albert.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.albert.entity.Book;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book>
{
}
