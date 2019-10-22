package com.apple.service.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.apple.api.response.Author;
import com.apple.repository.converter.Model2DomainAuthorConverter;

import static org.junit.jupiter.api.Assertions.*;

public class Domain2ApiAuthorConverterTest {

    private static final Long ID = 122L;
    private static final String FIRST_NAME = "Tamara";
    private static final String LAST_NAME = "Xenokrates";

    private Domain2ApiAuthorConverter victim;

    @BeforeEach
    public void setUp() {
        victim = new Domain2ApiAuthorConverter();
    }

    @Test
    public void shouldConvertAuthor() {
        com.apple.domain.Author domainAuthor = new com.apple.domain.Author(ID, FIRST_NAME, LAST_NAME);

        Author target = victim.convert(domainAuthor);

        assertEquals(target.getId(), ID);
        assertEquals(target.getFirstName(), FIRST_NAME);
        assertEquals(target.getLastName(), LAST_NAME);
    }

}