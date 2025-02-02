package br.com.bookstore.application;

import br.com.bookstore.adapter.input.dto.AuthorAndBooksResponse;
import br.com.bookstore.adapter.input.dto.AuthorRequest;
import br.com.bookstore.adapter.input.dto.AuthorResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AuthorUseCase {
    Flux<AuthorResponse> getListAuthors();
    Flux<AuthorAndBooksResponse> getListAuthorsAndBooks();
    Mono<AuthorResponse> getAuthorById(Long id);
    Mono<AuthorResponse> saveAuthor(AuthorRequest authorRequest);
    Mono<AuthorResponse> updateAuthor(Long id, AuthorRequest authorRequest);
    Mono<Void> deleteAuthor(Long id);
}
