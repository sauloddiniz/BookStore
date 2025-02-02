//package br.com.bookstore.adapter.input;
//
//import br.com.bookstore.adapter.input.dto.AuthorAndBooksResponse;
//import br.com.bookstore.adapter.input.dto.BookRequest;
//import br.com.bookstore.adapter.input.dto.BookResponse;
//import br.com.bookstore.application.BookUseCase;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/authors/{authorId}/books")
//public class BookController {
//
//    private final BookUseCase bookUseCase;
//
//    public BookController(BookUseCase bookUseCase) {
//        this.bookUseCase = bookUseCase;
//    }
//
//    @GetMapping()
//    public ResponseEntity<AuthorAndBooksResponse> getBooks(@PathVariable Long authorId) {
//        return ResponseEntity.ok(bookUseCase.getListBooks(authorId));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<AuthorAndBooksResponse> getBookById(@PathVariable Long authorId, @PathVariable Long id) {
//        return ResponseEntity.ok(bookUseCase.getBookById(authorId, id));
//    }
//
//    @PostMapping()
//    public ResponseEntity<Void> saveBook(@PathVariable Long authorId, @RequestBody BookRequest bookRequest) {
//        Long id = bookUseCase.saveBook(authorId, bookRequest);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/authors/{authorId}/books/{id}")
//                .buildAndExpand(authorId.toString(), id.toString())
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<BookResponse> updateBook(@PathVariable Long authorId,
//                                                   @PathVariable Long id,
//                                                   @RequestBody BookRequest bookRequest) {
//        BookResponse authorResponse = bookUseCase.updateBook(authorId, id, bookRequest);
//        return ResponseEntity.ok(authorResponse);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBook(@PathVariable Long authorId,
//                                           @PathVariable Long id) {
//        bookUseCase.deleteBook(authorId, id);
//        return ResponseEntity.noContent().build();
//    }
//}
