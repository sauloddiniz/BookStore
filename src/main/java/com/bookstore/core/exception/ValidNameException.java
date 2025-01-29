package com.bookstore.core.exception;

public class ValidNameException extends RuntimeException {

    private static final String MESSAGE = "Name invalid: ";

    public ValidNameException() {
        super(MESSAGE);
    }
}
