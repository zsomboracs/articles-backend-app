package com.apple.service.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.apple.api.request.SaveArticle;
import com.apple.api.request.UpdateArticle;
import com.apple.api.response.Article;
import com.apple.service.business.ArticleService;
import com.apple.service.converter.Domain2ApiArticleConverter;
import com.apple.service.exception.BadRequestException;

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
    public Article article(@RequestBody SaveArticle article) {
        return articleConverter.convert(
                articleService.save(
                        article.getTitle(),
                        article.getText(),
                        article.getSummary(),
                        article.getAuthorId()));
    }

    // TODO introduce pagination with max limit on page size
    @GetMapping("/articles")
    public List<Article> articles() {
        return articleService.articles()
                .stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList());
    }

    // TODO in production environment a more sophisticated approach would be better (e.g. application/merge-patch+json with JSON-P)
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

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
