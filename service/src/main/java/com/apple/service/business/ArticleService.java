package com.apple.service.business;

import java.util.List;
import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apple.domain.Article;
import com.apple.repository.ArticleRepositoryFacade;
import com.apple.repository.crud.ArticleRepository;
import com.apple.repository.crud.AuthorRepository;
import com.apple.service.exception.BadRequestException;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class ArticleService {

    private static final String SHOULD_BE_EXISTING_ARTICLE_ID = "article id should be an existing article";

    private ArticleRepositoryFacade articleRepositoryFacade;
    private ArticleRepository articleRepository;
    private AuthorRepository authorRepository;

    @Inject
    public ArticleService(ArticleRepositoryFacade articleRepositoryFacade,
                          ArticleRepository articleRepository,
                          AuthorRepository authorRepository) {
        this.articleRepositoryFacade = articleRepositoryFacade;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Article save(String title, String text, String summary, Long authorId) {

        checkArgument(title != null, "article title can not be null");
        checkArgument(text != null, "article text can not be null");
        checkArgument(authorId != null, "author id can not be null");
        checkArgument(authorRepository.existsById(authorId), "author id should be an existing author");

        return articleRepositoryFacade.save(title, text, summary, authorId);
    }

    public List<Article> articles() {
        return articleRepositoryFacade.articles();
    }

    @Transactional
    public void updateTitle(Long id, String newTitle) {
        articleRepositoryFacade.updateTitle(id, newTitle).orElseThrow(
                () -> new BadRequestException(SHOULD_BE_EXISTING_ARTICLE_ID));
    }

    @Transactional
    public void updateText(Long id, String newText) {
        articleRepositoryFacade.updateText(id, newText).orElseThrow(
                () -> new BadRequestException(SHOULD_BE_EXISTING_ARTICLE_ID));
    }

    @Transactional
    public void updateSummary(Long id, String newSummary) {
        articleRepositoryFacade.updateSummary(id, newSummary).orElseThrow(
                () -> new BadRequestException(SHOULD_BE_EXISTING_ARTICLE_ID));
    }

    @Transactional
    public void delete(Long id) {
        try {
            articleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequestException(SHOULD_BE_EXISTING_ARTICLE_ID);
        }
    }

}
