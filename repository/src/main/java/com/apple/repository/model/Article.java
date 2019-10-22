package com.apple.repository.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.google.common.annotations.VisibleForTesting;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @Size(max = 100)
    private String title;

    @Size(max = 255)
    private String summary;

    private String text;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifyDate;

    @NotNull
    @ManyToOne
    private Author author;

    Article() {
    }

    public Article(String title, String summary, String text, Author author) {
        this.title = title;
        this.summary = summary;
        this.text = text;
        this.author = author;
    }

    @VisibleForTesting
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
