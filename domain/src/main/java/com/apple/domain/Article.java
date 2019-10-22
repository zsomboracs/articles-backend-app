package com.apple.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private Long id;
    private String title;
    private String summary;
    private String text;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Author author;

    public Article(Long id, String title, String summary, String text, LocalDateTime createDate, LocalDateTime modifyDate, Author author) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.text = text;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) &&
                Objects.equals(title, article.title) &&
                Objects.equals(summary, article.summary) &&
                Objects.equals(text, article.text) &&
                Objects.equals(createDate, article.createDate) &&
                Objects.equals(modifyDate, article.modifyDate) &&
                Objects.equals(author, article.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, text, createDate, modifyDate, author);
    }
}
