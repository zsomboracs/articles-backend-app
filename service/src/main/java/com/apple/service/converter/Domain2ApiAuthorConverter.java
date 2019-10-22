package com.apple.service.converter;

import org.springframework.stereotype.Component;
import com.apple.api.response.Author;

@Component
public class Domain2ApiAuthorConverter {

    public Author convert(com.apple.domain.Author author) {
        return new Author(
                author.getId(),
                author.getFirstName(),
                author.getLastName());
    }
}
