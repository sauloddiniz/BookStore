package br.com.bookstore.core.exception;

public class AuthorNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Author not found: ";

    public AuthorNotFoundException(String id) {
        super(MESSAGE.concat(id));
    }
}
