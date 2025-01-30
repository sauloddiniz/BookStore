package br.com.bookstore.adapter.input;

import br.com.bookstore.adapter.configuration.JwtUtil;
import br.com.bookstore.adapter.persistence.entity.AuthorEntity;
import br.com.bookstore.adapter.persistence.entity.BookEntity;
import br.com.bookstore.adapter.persistence.entity.Category;
import br.com.bookstore.adapter.persistence.repository.AuthorRepository;
import br.com.bookstore.adapter.persistence.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorRepository authorRepository;

    @MockitoBean
    private BookRepository bookRepository;

    private MockedStatic<JwtUtil> jwtUtilMock;

    @BeforeEach
    void setUp() {
        jwtUtilMock = mockStatic(JwtUtil.class);
        jwtUtilMock.when(() -> JwtUtil.validJwt(anyString()))
                .thenReturn(true);
        jwtUtilMock.when(() -> JwtUtil.getEmail(anyString()))
                .thenReturn("my_email");
    }

    @AfterEach
    void tearDown() {
        jwtUtilMock.close();
    }

    @Test
    @DisplayName("Should return books for a given author when the author exists")
    void shouldReturnBooksForAuthorWhenAuthorExists() throws Exception {

        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(getAuthorEntityList().getFirst()));

        mockMvc.perform(get("/authors/{authorId}/books", 8L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8"))
                .andExpect(jsonPath("$.name").value("Viúva Negra"))
                .andExpect(jsonPath("$.books.length()").value(3))
                .andExpect(jsonPath("$.books[0].title").value("Segredos ocultos"))
                .andExpect(jsonPath("$.books[1].title").value("Raízes da realeza"))
                .andExpect(jsonPath("$.books[2].title").value("O guardião do tempo"));
    }

    @Test
    @DisplayName("Should return books for a given author when the author exists but has no books")
    void shouldReturnBooksForAuthorWhenAuthorExistsButHasNoBooks() throws Exception {
        Mockito.when(authorRepository.findById(1L))
                .thenReturn(Optional.of(getAuthorEntityList().get(1)));

        mockMvc.perform(get("/authors/{authorId}/books", 1L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Thor"))
                .andExpect(jsonPath("$.books.length()").value(0));
    }

    @Test
    @DisplayName("Should return the correct book by ID when the author and book exist")
    void shouldReturnBookByIdWhenAuthorAndBookExist() throws Exception {

        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(getAuthorEntityList().getFirst()));

        mockMvc.perform(get("/authors/{authorId}/books/{id}", 8L, 9L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8"))
                .andExpect(jsonPath("$.name").value("Viúva Negra"))
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].id").value(9))
                .andExpect(jsonPath("$.books[0].title").value("Raízes da realeza"))
                .andExpect(jsonPath("$.books[0].category").value("Ficção"));
    }

    @Test
    @DisplayName("Should return 404 when the author does not exist")
    void shouldReturn404WhenAuthorDoesNotExist() throws Exception {
        Mockito.when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/authors/{authorId}/books/{id}", 999L, 1L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when the book does not exist for an existing author")
    void shouldReturn404WhenBookDoesNotExistForAuthor() throws Exception {

        Mockito.when(authorRepository.findById(1L))
                .thenReturn(Optional.of(getAuthorEntityList().get(1)));

        mockMvc.perform(get("/authors/{authorId}/books/{id}", 8L, 999L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should successfully save a book when the author exists")
    void shouldSaveBookForExistingAuthor() throws Exception {

        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(getAuthorEntityList().getFirst()));
        Mockito.when(bookRepository.save(any(BookEntity.class)))
                .thenReturn(new BookEntity(11L, "Nova Aventura", "Uma nova história fascinante", Category.DRAMA));

        mockMvc.perform(post("/authors/{authorId}/books", 8L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Nova Aventura",
                                    "description": "Uma nova história fascinante",
                                    "category": "DRAMA"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/authors/8/books/11"));
    }

    @Test
    @DisplayName("Should return 400 when trying to save a book with an empty title")
    void shouldReturn400WhenSavingBookWithEmptyTitle() throws Exception {

        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(getAuthorEntityList().getFirst()));

        mockMvc.perform(post("/authors/{authorId}/books", 8L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "",
                                "description": "Uma nova história fascinante",
                                "category": "DRAMA"
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Title invalid: "));
    }

    @Test
    @DisplayName("Should return 404 when trying to save a book for a non-existing author")
    void shouldReturn404WhenSavingBookForNonExistingAuthor() throws Exception {

        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/authors/{authorId}/books", 999L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Nova Aventura",
                                    "description": "Uma nova história fascinante",
                                    "category": "DRAMA"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 400 when trying to save a book with an invalid category")
    void shouldReturn400WhenSavingBookWithInvalidCategory() throws Exception {

        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(new AuthorEntity(1L, "Thor", List.of())));

        mockMvc.perform(post("/authors/{authorId}/books", 1L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Nova Aventura",
                                "description": "Uma nova história fascinante",
                                "category": "FAKE CATEGORY"
                            }
                            """))
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("Should update book successfully when both author and book exist")
    void shouldUpdateBookSuccessfullyWhenAuthorAndBookExist() throws Exception {
        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(getAuthorEntityList().getFirst()));

        Mockito.when(bookRepository.save(any(BookEntity.class)))
                .thenReturn(new BookEntity(9L, "Updated Title", "Updated Description", Category.FICCAO));

        mockMvc.perform(put("/authors/{authorId}/books/{id}", 8L, 9L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Updated Title",
                                    "description": "Updated Description",
                                    "category": "FICCAO"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9L))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.category").value("Ficção"));
    }

    @Test
    @DisplayName("Should return 404 when trying to update a book for a non-existing author")
    void shouldReturn404WhenUpdatingBookForNonExistingAuthor() throws Exception {
        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/authors/{authorId}/books/{id}", 999L, 9L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Updated Title",
                                    "description": "Updated Description",
                                    "category": "FICCAO"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when trying to update a non-existing book for an existing author")
    void shouldReturn404WhenUpdatingNonExistingBookForAuthor() throws Exception {
        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(getAuthorEntityList().getFirst()));
        Mockito.when(bookRepository.findById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/authors/{authorId}/books/{id}", 8L, 999L)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Updated Title",
                                    "description": "Updated Description",
                                    "category": "FICCAO"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should successfully delete a book when both author and book exist")
    void shouldDeleteBookSuccessfullyWhenAuthorAndBookExist() throws Exception {
        Mockito.when(authorRepository.findById(1L))
                .thenReturn(Optional.of(getAuthorEntityList().getFirst()));
        Mockito.when(bookRepository.findById(9L))
                .thenReturn(Optional.of(new BookEntity(9L, "Raízes da realeza", "Description", Category.FICCAO)));

        mockMvc.perform(delete("/authors/{authorId}/books/{id}", 1L, 9L)
                        .header("Authorization", "Bearer my_token_is_valid"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 when trying to delete a book for a non-existing author")
    void shouldReturn404WhenDeletingForNonExistingAuthor() throws Exception {
        Mockito.when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/authors/{authorId}/books/{id}", 999L, 9L)
                        .header("Authorization", "Bearer my_token_is_valid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when trying to delete a non-existing book for an existing author")
    void shouldReturn404WhenDeletingNonExistingBook() throws Exception {
        Mockito.when(authorRepository.findById(1L))
                .thenReturn(Optional.of(getAuthorEntityList().getFirst()));
        Mockito.when(bookRepository.findById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/authors/{authorId}/books/{id}", 1L, 999L)
                        .header("Authorization", "Bearer my_token_is_valid"))
                .andExpect(status().isNotFound());
    }

    private static List<AuthorEntity> getAuthorEntityList() {
        return List.of(
                new AuthorEntity(8L, "Viúva Negra",
                        List.of(new BookEntity(8L, "Segredos ocultos", "Uma jornada de espionagem e mistério", Category.POESIA),
                                new BookEntity(9L, "Raízes da realeza", "A luta pelo poder e pela preservação das tradições", Category.FICCAO),
                                new BookEntity(10L, "O guardião do tempo", "História fascinante sobre magia e escolhas", Category.DRAMA))),
                new AuthorEntity(1L, "Thor")
        );
    }

}
