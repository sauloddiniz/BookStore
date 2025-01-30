package br.com.bookstore.core.exception;

public class ValidTitleException extends RuntimeException {

    private static final String MESSAGE = "Title invalid: ";

    public ValidTitleException() {
        super(MESSAGE);
    }
}
