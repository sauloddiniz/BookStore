package br.com.bookstore.adapter.input;

import br.com.bookstore.adapter.configuration.JwtUtil;
import br.com.bookstore.adapter.persistence.entity.AuthorEntity;
import br.com.bookstore.adapter.persistence.entity.BookEntity;
import br.com.bookstore.adapter.persistence.entity.Category;
import br.com.bookstore.adapter.persistence.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorRepository authorRepository;

    @MockitoBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        when(jwtUtil.validJwt(anyString()))
                .thenReturn(true);
        when(jwtUtil.getEmail(anyString()))
                .thenReturn("my_email");
    }

    @Test
    @DisplayName("Should Return List Of Authors When Books Param Is False")
    void shouldReturnListOfAuthorsWhenBooksParamIsFalse() throws Exception {
        List<AuthorEntity> authorEntities = getAuthorEntityList();

        when(authorRepository.findAll())
                .thenReturn(authorEntities);

        mockMvc.perform(get("/authors?books=false")
                        .header("Authorization", "Bearer my_token_is_valid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Viúva Negra"))
                .andExpect(jsonPath("$[1].name").value("Thor"));
    }

    @Test
    @DisplayName("Should Return Authors With Their Books When Books Param Is True")
    void shouldReturnAuthorsWithTheirBooksWhenBooksParamIsTrue() throws Exception {
        List<AuthorEntity> authorEntities = getAuthorEntityList();

        when(authorRepository.findAll())
                .thenReturn(authorEntities);

        mockMvc.perform(get("/authors?books=true")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(8))
                .andExpect(jsonPath("$[0].name").value("Viúva Negra"))
                .andExpect(jsonPath("$[0].books.length()").value(3))

                .andExpect(jsonPath("$[0].books[0].id").value(8))
                .andExpect(jsonPath("$[0].books[0].title").value("Segredos ocultos"))
                .andExpect(jsonPath("$[0].books[0].description").value("Uma jornada de espionagem e mistério"))
                .andExpect(jsonPath("$[0].books[0].category").value("Poesia"))

                .andExpect(jsonPath("$[0].books[1].id").value(9))
                .andExpect(jsonPath("$[0].books[1].title").value("Raízes da realeza"))
                .andExpect(jsonPath("$[0].books[1].description").value("A luta pelo poder e pela preservação das tradições"))
                .andExpect(jsonPath("$[0].books[1].category").value("Ficção"))

                .andExpect(jsonPath("$[0].books[2].id").value(10))
                .andExpect(jsonPath("$[0].books[2].title").value("O guardião do tempo"))
                .andExpect(jsonPath("$[0].books[2].description").value("História fascinante sobre magia e escolhas"))
                .andExpect(jsonPath("$[0].books[2].category").value("Drama"))

                .andExpect(jsonPath("$[1].id").value(1))
                .andExpect(jsonPath("$[1].name").value("Thor"))
                .andExpect(jsonPath("$[1].books.length()").value(0));
    }

    @Test
    @DisplayName("Should Return Author By ID When Author Exists")
    void shouldReturnAuthorByIdWhenAuthorExists() throws Exception {

        Long authorId = 1L;
        when(authorRepository.findById(authorId))
                .thenReturn(java.util.Optional.of(new AuthorEntity(1L, "Thor")));

        mockMvc.perform(get("/authors/1")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Thor"));
    }

    @Test
    @DisplayName("Should Return 404 Not Found When Author Does Not Exist")
    void shouldReturnAuthorByIdWhenAuthorDoesNotExist() throws Exception {
        Long authorId = 99L;
        when(authorRepository.findById(authorId))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/authors/99")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Successfully Save a New Author")
    void shouldSuccessfullySaveANewAuthor() throws Exception {

        when(authorRepository.save(any(AuthorEntity.class)))
                .thenReturn(new AuthorEntity(5L, "New Author"));

        mockMvc.perform(post("/authors")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": null,
                                    "name": "New Author"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/author/5"));
    }

    @Test
    @DisplayName("Should Return 400 Bad Request for Invalid Author Data")
    void shouldReturnBadRequestForInvalidAuthorData() throws Exception {
        mockMvc.perform(post("/authors")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": null,
                                    "name": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should Successfully Update an Existing Author")
    void shouldSuccessfullyUpdateAnExistingAuthor() throws Exception {
        Long authorId = 1L;
        AuthorEntity authorRequest = new AuthorEntity(authorId, "Updated Author");
        AuthorEntity updatedAuthor = new AuthorEntity(authorId, "Updated Author");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorRequest));
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(updatedAuthor);

        mockMvc.perform(put("/authors/{id}", authorId)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "Updated Author"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorId))
                .andExpect(jsonPath("$.name").value("Updated Author"));
    }

    @Test
    @DisplayName("Should Return 404 Not Found When Updating a Non-Existent Author")
    void shouldReturnNotFoundWhenUpdatingNonExistentAuthor() throws Exception {
        Long authorId = 99L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/authors/{id}", authorId)
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 99,
                                    "name": "Non-Existent Author"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Successfully Delete an Existing Author")
    void shouldSuccessfullyDeleteAnExistingAuthor() throws Exception {
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(new AuthorEntity(1L, "Thor")));
        doNothing().when(authorRepository).deleteById(authorId);

        mockMvc.perform(delete("/authors/{id}", authorId)
                        .header("Authorization", "Bearer my_token_is_valid"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should Return 404 Not Found When Deleting a Non-Existent Author")
    void shouldReturnNotFoundWhenDeletingNonExistentAuthor() throws Exception {
        Long authorId = 99L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/authors/{id}", authorId)
                        .header("Authorization", "Bearer my_token_is_valid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Return 401 Unauthorized When Authorization Header Is Missing")
    void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissing() throws Exception {
        Long authorId = 99L;

        mockMvc.perform(delete("/authors/{id}", authorId))
                .andExpect(status().isUnauthorized());
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
