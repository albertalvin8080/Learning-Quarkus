package org.albert.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.albert.entity.Author;

import java.util.List;

@ApplicationScoped
public class AuthorRepository implements PanacheMongoRepository<Author>
{
    public List<Author> findByFirstName(String firstName)
    {
//        return this.find("firstName", firstName).stream().toList();
        return this.find("{ firstName: ?1 }", firstName).stream().toList();
    }
}
