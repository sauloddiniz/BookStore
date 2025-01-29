package com.bookstore.adapter.output;

import com.bookstore.core.domain.Book;

public interface BookPersistencePort {
    Book saveBook(Book book);
    void deleteBook(Book book);
}
