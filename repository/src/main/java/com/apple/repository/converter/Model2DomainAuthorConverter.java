package com.apple.repository.converter;

import org.springframework.stereotype.Component;
import com.apple.domain.Author;

@Component
public class Model2DomainAuthorConverter {

    public Author convert(com.apple.repository.model.Author author) {
        return new Author(
                author.getId(),
                author.getFirstName(),
                author.getLastName());
    }
}
