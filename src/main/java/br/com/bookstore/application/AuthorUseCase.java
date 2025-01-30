package br.com.bookstore.application;

import br.com.bookstore.adapter.input.dto.AuthorAndBooksResponse;
import br.com.bookstore.adapter.input.dto.AuthorRequest;
import br.com.bookstore.adapter.input.dto.AuthorResponse;

import java.util.List;

public interface AuthorUseCase {
    List<AuthorResponse> getListAuthors();
    AuthorResponse getAuthorById(Long id);
    Long saveAuthor(AuthorRequest authorRequest);
    AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest);
    void deleteAuthor(Long id);
    List<AuthorAndBooksResponse> getListAuthorsAndBooks();
}
