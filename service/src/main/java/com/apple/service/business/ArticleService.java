package com.apple.service.business;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apple.domain.Article;
import com.apple.repository.ArticleRepositoryFacade;
import com.apple.repository.crud.AuthorRepository;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class ArticleService {

    private ArticleRepositoryFacade articleRepositoryFacade;
    private AuthorRepository authorRepository;

    @Inject
    public ArticleService(ArticleRepositoryFacade articleRepositoryFacade, AuthorRepository authorRepository) {
        this.articleRepositoryFacade = articleRepositoryFacade;
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
}