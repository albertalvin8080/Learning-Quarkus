package org.albert.mapstruct;

import org.albert.dto.AuthorDTO;
import org.albert.entity.Author;
import org.mapstruct.*;

@Mapper(
        componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuthorMapper
{
    @Mapping(target = "id", ignore = true)
    Author toDAO(AuthorDTO authorDTO);

    @Mappings({
            @Mapping(
                    // It's a new property only inside AuthorDTO.
                    target = "fullName",
                    // Using mapstruct expression.
                    expression = "java(author.getFirstName() + ' ' + author.getSecondName())"
            ),
            @Mapping(target = "id", ignore = false)
    })
    AuthorDTO toDTO(Author author);

    // It's useful for example when the provided authorDTO has only the secondName. The
    // resulting author will have the new secondName and the old firstName.
    // Remember to set nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE.
    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget Author author, AuthorDTO authorDTO);
}
