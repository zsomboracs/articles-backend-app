package com.apple.repository;

import java.util.List;
import java.util.Optional;

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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleRepositoryFacadeTest {

    private static final Long ARTICLE_ID = 1L;
    private static final Long AUTHOR_ID = 122L;
    private static final String TITLE = "The Most Important Question of Your Life";
    private static final String TITLE_2 = "The Most Important Question of Your Life 2";
    private static final String SUMMARY = "summary";
    private static final String TEXT = "TBD";

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

    @Test
    public void shouldReturnArticles() {
        com.apple.repository.model.Article modelArticle1 = new com.apple.repository.model.Article(TITLE, null, null, null);
        com.apple.repository.model.Article modelArticle2 = new com.apple.repository.model.Article(TITLE_2, null, null, null);

        Article domainArticle1 = new Article(null, TITLE, null, null, null, null, null);
        Article domainArticle2 = new Article(null, TITLE_2, null, null, null, null, null);

        when(articleRepository.findByOrderByTitleAsc()).thenReturn(List.of(modelArticle1, modelArticle2));
        when(articleConverter.convert(eq(modelArticle1))).thenReturn(domainArticle1);
        when(articleConverter.convert(eq(modelArticle2))).thenReturn(domainArticle2);

        List<Article> target = victim.articles();

        assertEquals(target, List.of(domainArticle1, domainArticle2));
    }

    @Test
    public void shouldUpdateTitle() {
        com.apple.repository.model.Article modelArticle = new com.apple.repository.model.Article(TITLE, null, null, null);
        Article domainArticle = new Article(ARTICLE_ID, TITLE, null, null, null, null, null);

        when(articleRepository.findById(eq(ARTICLE_ID))).thenReturn(Optional.of(modelArticle));
        when(articleConverter.convert(eq(modelArticle))).thenReturn(domainArticle);
        when(articleRepository.save(eq(modelArticle))).thenReturn(modelArticle);

        Optional<Article> target = victim.updateTitle(ARTICLE_ID, TITLE);

        assertTrue(target.isPresent());
        assertEquals(target.get().getId(), ARTICLE_ID);
        assertEquals(target.get().getTitle(), TITLE);
    }

    @Test
    public void shouldUpdateText() {
        com.apple.repository.model.Article modelArticle = new com.apple.repository.model.Article(null, null, TEXT, null);
        Article domainArticle = new Article(ARTICLE_ID, null, null, TEXT, null, null, null);

        when(articleRepository.findById(eq(ARTICLE_ID))).thenReturn(Optional.of(modelArticle));
        when(articleConverter.convert(eq(modelArticle))).thenReturn(domainArticle);
        when(articleRepository.save(eq(modelArticle))).thenReturn(modelArticle);

        Optional<Article> target = victim.updateText(ARTICLE_ID, TEXT);

        assertTrue(target.isPresent());
        assertEquals(target.get().getId(), ARTICLE_ID);
        assertEquals(target.get().getText(), TEXT);
    }

    @Test
    public void shouldUpdateSummary() {
        com.apple.repository.model.Article modelArticle = new com.apple.repository.model.Article(null, SUMMARY, null, null);
        Article domainArticle = new Article(ARTICLE_ID, null, SUMMARY, null, null, null, null);

        when(articleRepository.findById(eq(ARTICLE_ID))).thenReturn(Optional.of(modelArticle));
        when(articleConverter.convert(eq(modelArticle))).thenReturn(domainArticle);
        when(articleRepository.save(eq(modelArticle))).thenReturn(modelArticle);

        Optional<Article> target = victim.updateSummary(ARTICLE_ID, SUMMARY);

        assertTrue(target.isPresent());
        assertEquals(target.get().getId(), ARTICLE_ID);
        assertEquals(target.get().getSummary(), SUMMARY);
    }

}