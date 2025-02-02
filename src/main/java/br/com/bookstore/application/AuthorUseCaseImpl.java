package br.com.bookstore.application;

import br.com.bookstore.adapter.input.dto.AuthorAndBooksResponse;
import br.com.bookstore.adapter.input.dto.AuthorRequest;
import br.com.bookstore.adapter.input.dto.AuthorResponse;
import br.com.bookstore.adapter.output.AuthorPersistencePort;
import br.com.bookstore.core.domain.Author;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorUseCaseImpl implements AuthorUseCase {

    private final AuthorPersistencePort authorPersistencePort;

    public AuthorUseCaseImpl(AuthorPersistencePort authorPersistencePort) {
        this.authorPersistencePort = authorPersistencePort;
    }

    @Override
    public Flux<AuthorResponse> getListAuthors() {
        return authorPersistencePort
                .getListAuthors()
                .map(AuthorResponse::toResponse);
    }

    @Override
    public Flux<AuthorAndBooksResponse> getListAuthorsAndBooks() {
        return authorPersistencePort
                .getListAuthors()
                .map(AuthorAndBooksResponse::toResponse);
    }

    @Override
    public Mono<AuthorResponse> getAuthorById(Long id) {
        return authorPersistencePort
                .getAuthorById(id)
                .map(AuthorResponse::toResponse);
    }

    @Override
    public Mono<AuthorResponse> saveAuthor(AuthorRequest authorRequest) {
        return authorPersistencePort
                .saveAuthor(new Author(authorRequest.id(), authorRequest.name()))
                .map(AuthorResponse::toResponse);
    }

    @Override
    public Mono<AuthorResponse> updateAuthor(Long id, AuthorRequest authorRequest) {
        return authorPersistencePort.getAuthorById(id)
                .flatMap(existingAuthor -> {
                    Author updatedAuthor = new Author(
                            existingAuthor.getId(),
                            authorRequest.name()
                    );
                    return authorPersistencePort.saveAuthor(updatedAuthor);
                })
                .map(AuthorResponse::toResponse);
    }

    @Override
    public Mono<Void> deleteAuthor(Long id) {
        return authorPersistencePort.getAuthorById(id)
                .flatMap(existingAuthor -> {
                    authorPersistencePort.deleteAuthor(existingAuthor.getId());
                    return Mono.empty();
                });
    }

}
