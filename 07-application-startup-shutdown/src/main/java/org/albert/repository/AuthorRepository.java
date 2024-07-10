package org.albert.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.albert.domain.Author;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author>
{
}
