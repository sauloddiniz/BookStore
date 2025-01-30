package br.com.bookstore.adapter.input.dto;

import br.com.bookstore.core.domain.Book;

public record BookResponse(Long id, String title, String description, String category) {

    public static BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getDescription(), book.getCategory().getDisplayName());
    }
}
