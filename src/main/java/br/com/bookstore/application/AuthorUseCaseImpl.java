package br.com.bookstore.application;

import br.com.bookstore.adapter.input.dto.AuthorAndBooksResponse;
import br.com.bookstore.adapter.input.dto.AuthorRequest;
import br.com.bookstore.adapter.input.dto.AuthorResponse;
import br.com.bookstore.adapter.output.AuthorPersistencePort;
import br.com.bookstore.core.domain.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorUseCaseImpl implements AuthorUseCase {

    private final AuthorPersistencePort authorPersistencePort;

    public AuthorUseCaseImpl(AuthorPersistencePort authorPersistencePort) {
        this.authorPersistencePort = authorPersistencePort;
    }

    @Override
    public List<AuthorResponse> getListAuthors() {
        List<Author> authors = authorPersistencePort.getListAuthors();
        return authors.stream().map(AuthorResponse::toResponse).toList();
    }

    @Override
    public List<AuthorAndBooksResponse> getListAuthorsAndBooks() {
        List<Author> authors = authorPersistencePort.getListAuthors();
        return authors.stream().map(AuthorAndBooksResponse::toResponse).toList();
    }

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = authorPersistencePort.getAuthorById(id);
        return AuthorResponse.toResponse(author);
    }

    @Override
    public Long saveAuthor(AuthorRequest authorRequest) {
        Author author = new Author(authorRequest.id(), authorRequest.name());
        author = authorPersistencePort.saveAuthor(author);
        return author.getId();
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        Author author = authorPersistencePort.getAuthorById(id);
        author = new Author(author.getId(), authorRequest.name());
        author = authorPersistencePort.saveAuthor(author);
        return AuthorResponse.toResponse(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorPersistencePort.getAuthorById(id);
        authorPersistencePort.deleteAuthor(author.getId());
    }

}
