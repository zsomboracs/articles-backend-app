package com.apple.service.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.apple.api.response.Article;
import com.apple.service.business.ArticleService;
import com.apple.service.converter.Domain2ApiArticleConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleControllerTest {

    private static final String TITLE = "The Most Important Question of Your Life";

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
        when(articleConverter.convert(eq(domainArticle))).thenReturn(new Article(null, TITLE, null, null, null, null, null));

        Article target = victim.article(new com.apple.api.request.Article(TITLE, null, null, null));

        assertEquals(target.getTitle(), TITLE);
    }
}