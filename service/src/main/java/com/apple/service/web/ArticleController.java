package com.apple.service.web;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.apple.api.response.Article;
import com.apple.service.business.ArticleService;
import com.apple.service.converter.Domain2ApiArticleConverter;

@RestController
public class ArticleController {

    private ArticleService articleService;
    private Domain2ApiArticleConverter articleConverter;

    @Inject
    public ArticleController(ArticleService articleService, Domain2ApiArticleConverter articleConverter) {
        this.articleService = articleService;
        this.articleConverter = articleConverter;
    }

    @PostMapping("/article")
    public Article article(@RequestBody com.apple.api.request.Article article) {
        return articleConverter.convert(
                articleService.save(
                        article.getTitle(),
                        article.getText(),
                        article.getSummary(),
                        article.getAuthorId()));
    }
}
