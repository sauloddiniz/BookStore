package br.com.bookstore.adapter.persistence.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table(value = "book", schema = "bookstore")
public class BookEntity implements Serializable {

    @Id
    private Long id;

    private String title;
    private String description;
    private Category category;
    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public BookEntity() {
    }

    public BookEntity(String title, String description, Category category, Long authorId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.authorId = authorId;
    }

    public BookEntity(Long id, String title, String description, Category category, Long authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.authorId = authorId;
    }
}

