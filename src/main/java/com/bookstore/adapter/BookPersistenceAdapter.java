package com.bookstore.adapter;

import com.bookstore.adapter.output.BookPersistencePort;
import com.bookstore.adapter.persistence.entity.BookEntity;
import com.bookstore.adapter.persistence.entity.mapper.BookMapper;
import com.bookstore.adapter.persistence.repository.BookRepository;
import com.bookstore.core.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookPersistenceAdapter implements BookPersistencePort {

    private final BookRepository bookRepository;

    public BookPersistenceAdapter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(Book book) {
        BookEntity bookEntity = BookMapper.toEntity(book);
        return BookMapper.toBook(bookRepository.save(bookEntity));
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.deleteById(book.getId());
    }
}
