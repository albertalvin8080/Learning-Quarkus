package org.albert.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.albert.dto.AuthorDTO;
import org.albert.entity.Author;
import org.albert.mapstruct.AuthorMapper;
import org.albert.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthorService
{
    @Inject
    AuthorRepository repository;
    @Inject
    AuthorMapper authorMapper;

    public List<AuthorDTO> getAll()
    {
        return repository.listAll(Sort.by("id", Sort.Direction.Ascending))
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<AuthorDTO> create(AuthorDTO authorDTO)
    {
        final Author author = authorMapper.toDAO(authorDTO);
        repository.persist(author);
        return repository.isPersistent(author) ?
                Optional.of(authorMapper.toDTO(author)) :
                Optional.empty();
    }

    @Transactional
    public Optional<AuthorDTO> update(Long id, AuthorDTO authorDTO)
    {
        return repository.findByIdOptional(id)
                .map(author -> {
                    authorMapper.merge(author, authorDTO);
                    repository.persist(author);
                    return authorMapper.toDTO(author);
                });
    }
}
