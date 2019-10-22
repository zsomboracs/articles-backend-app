package com.apple.api.request;

public class Article {

    private String title;
    private String summary;
    private String text;
    private Long authorId;

    public Article(String title, String summary, String text, Long authorId) {
        this.title = title;
        this.summary = summary;
        this.text = text;
        this.authorId = authorId;
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

    public Long getAuthorId() {
        return authorId;
    }
}
