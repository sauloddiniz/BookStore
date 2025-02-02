package br.com.bookstore.adapter.output;

import br.com.bookstore.core.domain.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AuthorPersistencePort {
    Flux<Author> getListAuthors();
    Mono<Author> getAuthorById(Long id);
    Mono<Author> saveAuthor(Author author);
    void deleteAuthor(Long id);
}
