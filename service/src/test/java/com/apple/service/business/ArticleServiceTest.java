package com.apple.service.business;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.apple.domain.Article;
import com.apple.domain.Author;
import com.apple.repository.ArticleRepositoryFacade;
import com.apple.repository.crud.AuthorRepository;
import com.apple.service.exception.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleServiceTest {

    private static final Long AUTHOR_ID = 122L;
    private static final Long ARTICLE_ID = 12L;
    private static final String TITLE = "The Most Important Question of Your Life";
    private static final String SUMMARY = "summary";
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

    @Test
    public void shouldUpdateTitle() {
        Article domainArticle = new Article(ARTICLE_ID, TITLE, null, null, null, null, null);

        when(articleRepositoryFacade.updateTitle(eq(ARTICLE_ID), eq(TITLE))).thenReturn(Optional.of(domainArticle));

        victim.updateTitle(ARTICLE_ID, TITLE);

        verify(articleRepositoryFacade).updateTitle(eq(ARTICLE_ID), eq(TITLE));
    }

    @Test
    public void shouldThrowBadRequestExceptionIfArticleDoesNotExistInCaseOfUpdateTitle() {

        when(articleRepositoryFacade.updateTitle(eq(ARTICLE_ID), eq(TITLE))).thenReturn(Optional.empty());

        Throwable exception = assertThrows(
                BadRequestException.class,
                () -> victim.updateTitle(ARTICLE_ID, TITLE));

        assertEquals("article id should be an existing article", exception.getMessage());
    }

    @Test
    public void shouldUpdateText() {
        Article domainArticle = new Article(ARTICLE_ID, null, null, TEXT, null, null, null);

        when(articleRepositoryFacade.updateText(eq(ARTICLE_ID), eq(TEXT))).thenReturn(Optional.of(domainArticle));

        victim.updateText(ARTICLE_ID, TEXT);

        verify(articleRepositoryFacade).updateText(eq(ARTICLE_ID), eq(TEXT));
    }

    @Test
    public void shouldThrowBadRequestExceptionIfArticleDoesNotExistInCaseOfUpdateText() {

        when(articleRepositoryFacade.updateText(eq(ARTICLE_ID), eq(TEXT))).thenReturn(Optional.empty());

        Throwable exception = assertThrows(
                BadRequestException.class,
                () -> victim.updateText(ARTICLE_ID, TEXT));

        assertEquals("article id should be an existing article", exception.getMessage());
    }

    @Test
    public void shouldUpdateSummary() {
        Article domainArticle = new Article(ARTICLE_ID, null, SUMMARY, null, null, null, null);

        when(articleRepositoryFacade.updateSummary(eq(ARTICLE_ID), eq(SUMMARY))).thenReturn(Optional.of(domainArticle));

        victim.updateSummary(ARTICLE_ID, SUMMARY);

        verify(articleRepositoryFacade).updateSummary(eq(ARTICLE_ID), eq(SUMMARY));
    }

    @Test
    public void shouldThrowBadRequestExceptionIfArticleDoesNotExistInCaseOfUpdateSummary() {

        when(articleRepositoryFacade.updateSummary(eq(ARTICLE_ID), eq(SUMMARY))).thenReturn(Optional.empty());

        Throwable exception = assertThrows(
                BadRequestException.class,
                () -> victim.updateSummary(ARTICLE_ID, SUMMARY));

        assertEquals("article id should be an existing article", exception.getMessage());
    }


}