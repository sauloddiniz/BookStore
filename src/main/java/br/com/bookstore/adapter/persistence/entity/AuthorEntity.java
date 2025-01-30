package br.com.bookstore.adapter.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author", schema = "bookstore")
public class AuthorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BookEntity> books = new ArrayList<>();

    public AuthorEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorEntity(Long id, String name, List<BookEntity> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public AuthorEntity(Long id) {
        this.id = id;
    }

    public AuthorEntity() {
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public List<BookEntity> getBooks() {
        return books;
    }
}
