package com.bookstore.adapter.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "book", schema = "bookstore")
public class BookEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    public AuthorEntity getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public BookEntity() {
    }

    public BookEntity(String title, String description, Category category, Long authorId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.author = new AuthorEntity(authorId);
    }

    public BookEntity(Long id, String title, String description, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
    }
}
