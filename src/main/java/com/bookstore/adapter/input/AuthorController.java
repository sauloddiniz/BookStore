package com.bookstore.adapter.input;

import com.bookstore.adapter.input.dto.AuthorRequest;
import com.bookstore.adapter.input.dto.AuthorResponse;
import com.bookstore.application.AuthorUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorUseCase authorUseCase;

    public AuthorController(AuthorUseCase authorUseCase) {
        this.authorUseCase = authorUseCase;
    }

    @GetMapping
    public ResponseEntity<List<?>> getAuthors(@RequestParam(value = "books", required = false, defaultValue = "false") boolean books) {
        return Boolean.FALSE.equals(books)
                ? ResponseEntity.ok(authorUseCase.getListAuthors())
                : ResponseEntity.ok(authorUseCase.getListAuthorsAndBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorUseCase.getAuthorById(id));
    }

    @PostMapping()
    public ResponseEntity<Void> saveAuthor(@RequestBody AuthorRequest authorRequest) {
        Long id = authorUseCase.saveAuthor(authorRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/author/{id}")
                .buildAndExpand(id.toString())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable Long id,
                                             @RequestBody AuthorRequest authorRequest) {
        AuthorResponse authorResponse = authorUseCase.updateAuthor(id, authorRequest);
        return ResponseEntity.ok(authorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorUseCase.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
