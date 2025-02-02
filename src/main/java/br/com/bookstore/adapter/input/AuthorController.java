package br.com.bookstore.adapter.input;

import br.com.bookstore.adapter.input.dto.AuthorRequest;
import br.com.bookstore.adapter.input.dto.AuthorResponse;
import br.com.bookstore.application.AuthorUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<ResponseEntity<?>> getAuthors(@RequestParam(value = "books", required = false, defaultValue = "false") boolean books) {
        return Boolean.FALSE.equals(books)
                ? authorUseCase.getListAuthors().map(ResponseEntity::ok)
                : authorUseCase.getListAuthorsAndBooks().map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AuthorResponse>> getAuthorById(@PathVariable Long id) {
        return authorUseCase.getAuthorById(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping()
    public Mono<ResponseEntity<Void>> saveAuthor(@RequestBody AuthorRequest authorRequest) {
        return authorUseCase.saveAuthor(authorRequest)
                .map(authorResponse -> {
                    URI location = URI.create("/authors/" + authorResponse.id());
                    return ResponseEntity.created(location).build();
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AuthorResponse>> updateAuthor(@PathVariable Long id,
                                             @RequestBody AuthorRequest authorRequest) {
        return authorUseCase.updateAuthor(id, authorRequest).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable Long id) {
        return authorUseCase.deleteAuthor(id)
                .then(Mono.defer(() -> Mono.just(ResponseEntity.noContent().build())));
    }

}
