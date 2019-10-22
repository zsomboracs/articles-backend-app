package com.apple.domain;

import java.time.LocalDateTime;

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
}
