package com.apple.service.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import com.apple.api.response.Article;

@Component
public class Domain2ApiArticleConverter {

    private Domain2ApiAuthorConverter authorConverter;

    @Inject
    public Domain2ApiArticleConverter(Domain2ApiAuthorConverter authorConverter) {
        this.authorConverter = authorConverter;
    }

    public Article convert(com.apple.domain.Article article) {
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
