package com.apple.service.web;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.apple.api.request.SaveArticle;
import com.apple.api.request.UpdateArticle;
import com.apple.api.response.Article;
import com.apple.service.business.ArticleService;
import com.apple.service.converter.Domain2ApiArticleConverter;
import com.apple.service.exception.BadRequestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleControllerTest {

    private static final Long ARTICLE_ID = 12L;
    private static final String TITLE = "The Most Important Question of Your Life";
    private static final String SUMMARY = "Best post to start with to understand the underlying philosophy of my work.";
    private static final String TEXT = "TBD";
    private static final String TITLE_2 = "The Most Important Question of Your Life 2";

    @Mock
    private ArticleService articleService;

    @Mock
    private Domain2ApiArticleConverter articleConverter;

    private ArticleController victim;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        victim = new ArticleController(articleService, articleConverter);
    }


    @Test
    public void shouldReturnArticle() {
        com.apple.domain.Article domainArticle = new com.apple.domain.Article(null, TITLE, null, null, null, null, null);

        when(articleService.save(eq(TITLE), any(), any(), any())).thenReturn(domainArticle);
        when(articleConverter.convert(eq(domainArticle))).thenReturn(new com.apple.api.response.Article(null, TITLE, null, null, null, null, null));

        Article target = victim.article(new SaveArticle(TITLE, null, null, null));

        assertEquals(target.getTitle(), TITLE);
    }

    @Test
    public void shouldReturnArticles() {
        com.apple.domain.Article domainArticle1 = new com.apple.domain.Article(null, TITLE, null, null, null, null, null);
        com.apple.domain.Article domainArticle2 = new com.apple.domain.Article(null, TITLE_2, null, null, null, null, null);

        Article apiArticle1 = new Article(null, TITLE, null, null, null, null, null);
        Article apiArticle2 = new Article(null, TITLE_2, null, null, null, null, null);

        when(articleService.articles()).thenReturn(List.of(domainArticle1, domainArticle2));
        when(articleConverter.convert(eq(domainArticle1))).thenReturn(apiArticle1);
        when(articleConverter.convert(eq(domainArticle2))).thenReturn(apiArticle2);

        List<Article> target = victim.articles();

        assertEquals(target, List.of(apiArticle1, apiArticle2));
    }

    @Test
    public void shouldUpdateTitle() {
        ResponseEntity<Void> target = victim.partialUpdate(ARTICLE_ID, new UpdateArticle(TITLE, null, null));

        verify(articleService).updateTitle(eq(ARTICLE_ID), eq(TITLE));
        assertEquals(target.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldUpdateText() {
        ResponseEntity<Void> target = victim.partialUpdate(ARTICLE_ID, new UpdateArticle(null, null, TEXT));

        verify(articleService).updateText(eq(ARTICLE_ID), eq(TEXT));
        assertEquals(target.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldUpdateSummary() {
        ResponseEntity<Void> target = victim.partialUpdate(ARTICLE_ID, new UpdateArticle(null, SUMMARY, null));

        verify(articleService).updateSummary(eq(ARTICLE_ID), eq(SUMMARY));
        assertEquals(target.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldThrowBadRequestExceptionIfMoreThan1ParameterDefined() {

        Throwable exception = assertThrows(
                BadRequestException.class,
                () -> victim.partialUpdate(ARTICLE_ID, new UpdateArticle(TITLE, TEXT, SUMMARY)));

        assertEquals("Only one property at the time is updatable", exception.getMessage());
    }

    @Test
    public void shouldThrowBadRequestExceptionIfMoreLess1ParameterDefined() {

        Throwable exception = assertThrows(
                BadRequestException.class,
                () -> victim.partialUpdate(ARTICLE_ID, new UpdateArticle(null, null, null)));

        assertEquals("Only one property at the time is updatable", exception.getMessage());
    }

    @Test
    public void shouldDeleteArticle() {
        ResponseEntity<Void> target = victim.delete(ARTICLE_ID);

        verify(articleService).delete(eq(ARTICLE_ID));
        assertEquals(target.getStatusCode(), HttpStatus.NO_CONTENT);
    }

}