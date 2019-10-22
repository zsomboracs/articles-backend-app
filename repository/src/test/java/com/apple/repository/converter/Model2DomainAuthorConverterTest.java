package com.apple.repository.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apple.repository.model.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Model2DomainAuthorConverterTest {

    private static final Long ID = 122L;
    private static final String FIRST_NAME = "Tamara";
    private static final String LAST_NAME = "Xenokrates";

    private Model2DomainAuthorConverter victim;

    @BeforeEach
    public void setUp() {
        victim = new Model2DomainAuthorConverter();
    }

    @Test
    public void shouldConvertAuthor() {
        Author modelAuthor = new Author(ID, FIRST_NAME, LAST_NAME);

        com.apple.domain.Author target = victim.convert(modelAuthor);

        assertEquals(target.getId(), ID);
        assertEquals(target.getFirstName(), FIRST_NAME);
        assertEquals(target.getLastName(), LAST_NAME);
    }
}