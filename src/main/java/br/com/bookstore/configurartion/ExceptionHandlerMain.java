package br.com.bookstore.configurartion;

import br.com.bookstore.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerMain {

    @ExceptionHandler(AuthorNotFoundException.class)
    public Mono<Void> handleAuthorNotFoundException(AuthorNotFoundException ex, ServerWebExchange exchange) {
        return buildResponse(exchange, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public Mono<Void> handleBookNotFoundException(BookNotFoundException ex, ServerWebExchange exchange) {
        return buildResponse(exchange, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(ValidTitleException.class)
    public Mono<Void> handleValidTitleException(ValidTitleException ex, ServerWebExchange exchange) {
        return buildResponse(exchange, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(ValidNameException.class)
    public Mono<Void> handleValidNameException(ValidNameException ex, ServerWebExchange exchange) {
        return buildResponse(exchange, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(CategoryNotRegisterOrNullException.class)
    public Mono<Void> handleCategoryNotRegisterOrNullException(CategoryNotRegisterOrNullException ex,
                                                               ServerWebExchange exchange) {
        return buildResponse(exchange, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public Mono<Void> handleJwtAuthenticationException(JwtAuthenticationException ex, ServerWebExchange exchange) {
        return buildResponse(exchange, HttpStatus.UNAUTHORIZED, ex);
    }

    private Mono<Void> buildResponse(ServerWebExchange exchange, HttpStatus status, Exception exception) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("date", LocalDateTime.now());
        errorBody.put("error", exception.getMessage());
        errorBody.put("path", exchange.getRequest().getPath().value());

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(errorBody.toString().getBytes()))
        );
    }
}



