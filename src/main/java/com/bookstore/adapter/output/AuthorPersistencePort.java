package com.bookstore.adapter.output;

import com.bookstore.core.domain.Author;

import java.util.List;

public interface AuthorPersistencePort {
    List<Author> getListAuthors();
    Author getAuthorById(Long id);
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);
}
