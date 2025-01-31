package br.com.bookstore.adapter.persistence.entity.mapper;

import br.com.bookstore.adapter.persistence.entity.BookEntity;
import br.com.bookstore.core.domain.Author;
import br.com.bookstore.core.domain.Book;

import java.util.Optional;

public class BookMapper {
    private BookMapper() {}


    public static Book toBook(BookEntity bookEntity) {
        return new Book(bookEntity.getId(), bookEntity.getTitle(), bookEntity.getDescription(), bookEntity.getCategory(),
                Optional.ofNullable(bookEntity.getAuthor())
                        .map(AuthorMapper::toModelAndBookAndNameIsNull)
                        .orElse(null));
    }

    public static BookEntity toEntity(Book book) {
        return new BookEntity(book.getTitle(), book.getDescription(), book.getCategory(),
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getId)
                        .orElse(null));
    }
}
