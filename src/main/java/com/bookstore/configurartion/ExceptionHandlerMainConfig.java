package com.bookstore.configurartion;

import com.bookstore.core.exception.AuthorNotFoundException;
import com.bookstore.core.exception.BookNotFoundException;
import com.bookstore.core.exception.ValidNameException;
import com.bookstore.core.exception.ValidTitleException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerMainConfig {

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<Object> authorNotFoundException(AuthorNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFoundException(BookNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidTitleException.class)
    public ResponseEntity<Object> validTitleException(ValidTitleException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidNameException.class)
    public ResponseEntity<Object> validNameException(ValidNameException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private static Map<String, Object> extractErrorInfo(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("date", LocalDateTime.now());
        body.put("error", ex.getMessage());
        body.put("path", request.getRequestURI());
        body.put("method", request.getMethod());
        return body;
    }

}


