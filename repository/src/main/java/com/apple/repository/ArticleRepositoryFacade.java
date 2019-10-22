package com.apple.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import com.apple.domain.Article;
import com.apple.repository.converter.Model2DomainArticleConverter;
import com.apple.repository.crud.ArticleRepository;
import com.apple.repository.crud.AuthorRepository;
import com.apple.repository.model.Author;

@Repository
public class ArticleRepositoryFacade {

    private ArticleRepository articleRepository;
    private AuthorRepository authorRepository;
    private Model2DomainArticleConverter articleConverter;

    @Inject
    public ArticleRepositoryFacade(ArticleRepository articleRepository, AuthorRepository authorRepository, Model2DomainArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.articleConverter = articleConverter;
    }

    public Article save(String title, String text, String summary, Long authorId) {
        Author author = authorRepository.getOne(authorId);
        com.apple.repository.model.Article article = new com.apple.repository.model.Article(title, text, summary, author);
        return articleConverter.convert(articleRepository.saveAndFlush(article));
    }

    public List<Article> articles() {
        return articleRepository.findByOrderByTitleAsc()
                .stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList());
    }

    public Optional<Article> updateTitle(Long id, String newTitle) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(newTitle);
                    return articleRepository.save(article);
                })
                .map(article -> articleConverter.convert(article));
    }

    public Optional<Article> updateText(Long id, String newText) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setText(newText);
                    return articleRepository.save(article);
                })
                .map(article -> articleConverter.convert(article));
    }

    public Optional<Article> updateSummary(Long id, String newSummary) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setSummary(newSummary);
                    return articleRepository.save(article);
                })
                .map(article -> articleConverter.convert(article));
    }

}
