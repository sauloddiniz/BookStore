package com.bookstore.adapter.input.dto;

import com.bookstore.core.domain.Author;

public record AuthorResponse(Long id, String name) {

    public static AuthorResponse toResponse(Author author) {
        return new AuthorResponse(author.getId(), author.getName());
    }
}
