package br.com.bookstore.adapter.output;

import br.com.bookstore.core.domain.Book;

public interface BookPersistencePort {
    Book saveBook(Book book);
    void deleteBook(Book book);
}
