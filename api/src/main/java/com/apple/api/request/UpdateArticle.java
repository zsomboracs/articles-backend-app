package com.apple.api.request;

public class UpdateArticle {

    private String title;
    private String summary;
    private String text;

    public UpdateArticle(String title, String summary, String text) {
        this.title = title;
        this.summary = summary;
        this.text = text;
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
}
