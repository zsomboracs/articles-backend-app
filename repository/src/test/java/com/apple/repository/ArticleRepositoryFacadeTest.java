package com.apple.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.apple.domain.Article;
import com.apple.domain.Author;
import com.apple.repository.converter.Model2DomainArticleConverter;
import com.apple.repository.crud.ArticleRepository;
import com.apple.repository.crud.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleRepositoryFacadeTest {

    private static final Long AUTHOR_ID = 122L;
    private static final String TITLE = "The Most Important Question of Your Life";

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private Model2DomainArticleConverter articleConverter;

    private ArticleRepositoryFacade victim;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        victim = new ArticleRepositoryFacade(articleRepository, authorRepository, articleConverter);
    }

    @Test
    public void shouldSaveArticle() {
        com.apple.repository.model.Author modelAuthor = new com.apple.repository.model.Author(AUTHOR_ID, null, null);
        com.apple.repository.model.Article modelArticle = new com.apple.repository.model.Article(TITLE, null, null, modelAuthor);

        Author domainAuthor = new Author(AUTHOR_ID, null, null);
        Article domainArticle = new Article(null, TITLE, null, null, null, null, domainAuthor);

        when(authorRepository.getOne(eq(AUTHOR_ID))).thenReturn(modelAuthor);
        when(articleRepository.saveAndFlush(eq(modelArticle))).thenReturn(modelArticle);
        when(articleConverter.convert(eq(modelArticle))).thenReturn(domainArticle);

        Article target = victim.save(TITLE, null, null, AUTHOR_ID);

        assertEquals(target.getTitle(), TITLE);
        assertNotNull(target.getAuthor());
        assertEquals(target.getAuthor().getId(), AUTHOR_ID);
    }

}