package br.com.bookstore.core.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Book not found: ";

    public BookNotFoundException(String id) {
        super(MESSAGE.concat(id));
    }
}
