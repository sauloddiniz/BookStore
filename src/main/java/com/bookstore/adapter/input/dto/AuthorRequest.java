package com.bookstore.adapter.input.dto;

import com.bookstore.core.domain.Author;

public record AuthorRequest(Long id, String name) {

    public static AuthorRequest toRequest(Author author) {
        return new AuthorRequest(author.getId(), author.getName());
    }
}
