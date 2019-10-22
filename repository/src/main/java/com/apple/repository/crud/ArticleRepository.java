package com.apple.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apple.repository.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
