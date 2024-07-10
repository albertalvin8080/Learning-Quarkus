package org.albert;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.albert.domain.Author;
import org.albert.repository.AuthorRepository;

@ApplicationScoped
public class AppLifecycleBean
{
    @Inject
    AuthorRepository authorRepository;

    @Transactional
    public void startUp(@Observes StartupEvent startupEvent)
    {
        System.out.println("Inside startUp...");
        final Author author = new Author();
        author.setName("Franz Bonaparta");
        authorRepository.persist(author);
    }

    @Transactional
    public void shutDown(@Observes ShutdownEvent shutdownEvent)
    {
        System.out.println("Inside shutDown...");
        authorRepository.deleteAll();
    }
}
