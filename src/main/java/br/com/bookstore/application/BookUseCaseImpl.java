package br.com.bookstore.application;

import br.com.bookstore.adapter.input.dto.AuthorAndBooksResponse;
import br.com.bookstore.adapter.input.dto.BookRequest;
import br.com.bookstore.adapter.input.dto.BookResponse;
import br.com.bookstore.adapter.output.AuthorPersistencePort;
import br.com.bookstore.adapter.output.BookPersistencePort;
import br.com.bookstore.adapter.persistence.entity.Category;
import br.com.bookstore.core.domain.Author;
import br.com.bookstore.core.domain.Book;
import br.com.bookstore.core.exception.BookNotFoundException;
import br.com.bookstore.core.exception.CategoryNotRegisterOrNullException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookUseCaseImpl implements BookUseCase {

    private final AuthorPersistencePort authorPersistencePort;
    private final BookPersistencePort bookPersistencePort;

    public BookUseCaseImpl(AuthorPersistencePort authorPersistencePort, BookPersistencePort bookPersistencePort) {
        this.authorPersistencePort = authorPersistencePort;
        this.bookPersistencePort = bookPersistencePort;
    }

    @Override
    public AuthorAndBooksResponse getListBooks(Long authorId) {
        Author author = authorPersistencePort.getAuthorById(authorId);
        return AuthorAndBooksResponse.toResponse(author);
    }

    @Override
    public AuthorAndBooksResponse getBookById(Long authorId, Long id) {
        Author author = authorPersistencePort.getAuthorById(authorId);
        Book book = getBookByAuthorAndId(id, author);
        author.setOneBook(book);
        return AuthorAndBooksResponse.toResponse(author);
    }

    @Override
    public Long saveBook(Long authorId, BookRequest bookRequest) {
        Author author = authorPersistencePort.getAuthorById(authorId);
        Category category = getCategoryByName(bookRequest.category());
        Book book = new Book(bookRequest.title(),
                bookRequest.description(),
                category,
                author);
        book = bookPersistencePort.saveBook(book);
        return book.getId();
    }

    @Override
    public BookResponse updateBook(Long authorId, Long id, BookRequest bookRequest) {
        Author author = authorPersistencePort.getAuthorById(authorId);
        Book book = getBookByAuthorAndId(id, author);
        book = new Book(book.getId(), bookRequest.title(), bookRequest.description(),
                Category.valueOf(bookRequest.category().toUpperCase()), author);
        book = bookPersistencePort.saveBook(book);
        return BookResponse.toResponse(book);
    }

    @Override
    public void deleteBook(Long authorId, Long id) {
        Author author = authorPersistencePort.getAuthorById(authorId);
        Book book = getBookByAuthorAndId(id, author);
        bookPersistencePort.deleteBook(book);
    }

    private static Book getBookByAuthorAndId(Long id, Author author) {
        return author.getBooks().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id.toString()));
    }

    private Category getCategoryByName(String name) {
        return Optional.ofNullable(name)
                .map(String::toUpperCase)
                .map(cat -> {
                    try {
                        return Category.valueOf(cat);
                    } catch (IllegalArgumentException e) {
                        throw new CategoryNotRegisterOrNullException("Invalid category: " + name);
                    }
                })
                .orElseThrow(() -> new CategoryNotRegisterOrNullException("Category cannot be null"));
    }


}
