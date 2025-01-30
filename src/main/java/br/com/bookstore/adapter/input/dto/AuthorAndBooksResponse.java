package br.com.bookstore.adapter.input.dto;

import br.com.bookstore.core.domain.Author;

import java.util.List;
import java.util.Optional;

public record AuthorAndBooksResponse(Long id, String name, List<BookResponse> books) {


    public static AuthorAndBooksResponse toResponse(Author author) {
        return new AuthorAndBooksResponse(author.getId(), author.getName(),
                Optional.of(author.getBooks().stream().map(BookResponse::toResponse).toList()).orElse(List.of()));
    }

}
