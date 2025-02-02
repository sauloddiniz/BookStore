package br.com.bookstore.adapter.output;

import br.com.bookstore.core.domain.Book;
import reactor.core.publisher.Mono;

public interface BookPersistencePort {
    Mono<Book> saveBook(Book book);
    void deleteBook(Book book);
}
