package br.com.bookstore.adapter.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "author", schema = "bookstore")
public class AuthorEntity {

    @Id
    private Long id;

    private String name;

//    private transient List<BookEntity> books = new ArrayList<>();

    public AuthorEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

//    public AuthorEntity(Long id, String name, List<BookEntity> books) {
//        this.id = id;
//        this.name = name;
//        this.books = books;
//    }

    public AuthorEntity(Long id) {
        this.id = id;
    }

    public AuthorEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<BookEntity> getBooks() {
//        return books;
//    }
//
//    public void setBooks(List<BookEntity> books) {
//        this.books = books;
//    }
}

