package br.com.bookstore.adapter.input.dto;

import br.com.bookstore.core.domain.Author;

public record AuthorRequest(Long id, String name) {

    public static AuthorRequest toRequest(Author author) {
        return new AuthorRequest(author.getId(), author.getName());
    }
}
