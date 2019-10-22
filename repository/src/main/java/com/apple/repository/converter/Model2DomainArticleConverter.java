package com.apple.repository.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import com.apple.domain.Article;

@Component
public class Model2DomainArticleConverter {

    private Model2DomainAuthorConverter authorConverter;

    @Inject
    public Model2DomainArticleConverter(Model2DomainAuthorConverter authorConverter) {
        this.authorConverter = authorConverter;
    }

    public Article convert(com.apple.repository.model.Article article) {
        return new Article(
                article.getId(),
                article.getTitle(),
                article.getSummary(),
                article.getText(),
                article.getCreateDate(),
                article.getModifyDate(),
                authorConverter.convert(article.getAuthor()));
    }
}
