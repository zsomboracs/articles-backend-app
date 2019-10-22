package com.apple.service.web;

import java.util.stream.Stream;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.apple.api.request.SaveArticle;
import com.apple.api.request.UpdateArticle;
import com.apple.service.business.ArticleService;
import com.apple.service.converter.Domain2ApiArticleConverter;
import com.apple.service.exception.BadRequestException;

@RestController
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private ArticleService articleService;
    private Domain2ApiArticleConverter articleConverter;

    @Inject
    public ArticleController(ArticleService articleService, Domain2ApiArticleConverter articleConverter) {
        this.articleService = articleService;
        this.articleConverter = articleConverter;
    }

    @PostMapping("/article")
    public com.apple.api.response.Article article(@RequestBody SaveArticle article) {
        return articleConverter.convert(
                articleService.save(
                        article.getTitle(),
                        article.getText(),
                        article.getSummary(),
                        article.getAuthorId()));
    }

    @PatchMapping("/article/{id}")
    public ResponseEntity<Void> partialUpdate(@PathVariable Long id, @RequestBody UpdateArticle article) {

        if (Stream.of(article.getText(), article.getSummary(), article.getTitle())
                .filter(field -> field != null)
                .count() != 1) {
            throw new BadRequestException("Only one property at the time is updatable");
        }

        if (article.getTitle() != null) {
            articleService.updateTitle(id, article.getTitle());
        } else if (article.getText() != null) {
            articleService.updateText(id, article.getText());
        } else if (article.getSummary() != null) {
            articleService.updateSummary(id, article.getSummary());
        }

        return ResponseEntity.noContent().build();
    }

}
