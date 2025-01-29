package com.bookstore.core.domain;

import com.bookstore.adapter.persistence.entity.Category;
import com.bookstore.core.exception.ValidTitleException;

public class Book {
    private Long id;
    private String title;
    private String description;
    private Category category;
    private Author author;

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book() {
    }

    public Book(Long id, String title, String description, Category category, Author author) {
        validTitle(title);
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.author = author;
    }
    public Book(String title, String description, Category category, Author author) {
        validTitle(title);
        this.title = title;
        this.description = description;
        this.category = category;
        this.author = author;
    }

    private void validTitle(String title){
        if (title == null || title.isBlank()) {
            throw new ValidTitleException();
        }
    }
}
