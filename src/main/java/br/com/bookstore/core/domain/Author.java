package br.com.bookstore.core.domain;

import br.com.bookstore.core.exception.ValidNameException;

import java.util.ArrayList;
import java.util.List;

public class Author {

    private Long id;
    private String name;
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Author(Long id, String name) {
        this.id = id;
        validName(name);
        this.name = name;
    }

    public Author(Long id) {
        this.id = id;
    }

    public Author(Long id, String name, List<Book> books) {
        this.id = id;
        validName(name);
        this.name = name;
        this.books = books;
    }

    public void setOneBook(Book book) {
        this.books = new ArrayList<>();
        this.books.add(book);
    }

    private void validName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ValidNameException();
        }
    }

}
