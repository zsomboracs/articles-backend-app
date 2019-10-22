package com.apple.service.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.apple.domain.Article;
import com.apple.domain.Author;
import com.apple.repository.ArticleRepositoryFacade;
import com.apple.repository.crud.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleServiceTest {

    private static final Long AUTHOR_ID = 122L;
    private static final String TITLE = "The Most Important Question of Your Life";
    private static final String TEXT = "TBD";

    @Mock
    private ArticleRepositoryFacade articleRepositoryFacade;

    @Mock
    private AuthorRepository authorRepository;

    private ArticleService victim;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        victim = new ArticleService(articleRepositoryFacade, authorRepository);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfTitleIsNull() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> victim.save(null, TEXT, null, AUTHOR_ID));

        assertEquals("article title can not be null", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfTextIsNull() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> victim.save(TITLE, null, null, AUTHOR_ID));

        assertEquals("article text can not be null", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfAuthorIdIsNull() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> victim.save(TITLE, TEXT, null, null));

        assertEquals("author id can not be null", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfAuthorDoesNotExist() {
        when(authorRepository.existsById(eq(AUTHOR_ID))).thenReturn(false);

        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> victim.save(TITLE, TEXT, null, AUTHOR_ID));

        assertEquals("author id should be an existing author", exception.getMessage());
    }

    @Test
    public void shouldReturnArticle() {
        Author domainAuthor = new Author(AUTHOR_ID, null, null);
        Article domainArticle = new Article(null, TITLE, null, TEXT, null, null, domainAuthor);

        when(authorRepository.existsById(eq(AUTHOR_ID))).thenReturn(true);
        when(articleRepositoryFacade.save(eq(TITLE), eq(TEXT), any(), eq(AUTHOR_ID))).thenReturn(domainArticle);

        Article target = victim.save(TITLE, TEXT, null, AUTHOR_ID);

        assertEquals(target.getTitle(), TITLE);
        assertEquals(target.getText(), TEXT);
        assertNotNull(target.getAuthor());
        assertEquals(target.getAuthor().getId(), AUTHOR_ID);
    }


}