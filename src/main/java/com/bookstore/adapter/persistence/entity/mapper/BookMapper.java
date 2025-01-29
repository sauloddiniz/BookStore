package com.bookstore.adapter.persistence.entity.mapper;

import com.bookstore.adapter.persistence.entity.BookEntity;
import com.bookstore.core.domain.Author;
import com.bookstore.core.domain.Book;

import java.util.Optional;

public class BookMapper {
    private BookMapper() {}


    public static Book toBook(BookEntity bookEntity) {
        return new Book(bookEntity.getId(), bookEntity.getTitle(), bookEntity.getDescription(), bookEntity.getCategory(),
                Optional.ofNullable(bookEntity.getAuthor())
                        .map(AuthorMapper::toModelAndBookIsNull)
                        .orElse(null));
    }

    public static BookEntity toEntity(Book book) {
        return new BookEntity(book.getTitle(), book.getDescription(), book.getCategory(),
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getId)
                        .orElse(null));
    }
}
