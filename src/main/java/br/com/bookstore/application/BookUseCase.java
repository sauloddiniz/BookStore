package br.com.bookstore.application;

import br.com.bookstore.adapter.input.dto.AuthorAndBooksResponse;
import br.com.bookstore.adapter.input.dto.BookRequest;
import br.com.bookstore.adapter.input.dto.BookResponse;

public interface BookUseCase {
    AuthorAndBooksResponse getListBooks(Long authorId);
    AuthorAndBooksResponse getBookById(Long authorId, Long id);
    Long saveBook(Long authorId, BookRequest bookRequest);
    BookResponse updateBook(Long authorId, Long id, BookRequest bookRequest);
    void deleteBook(Long authorId, Long id);
}
