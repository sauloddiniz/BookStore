package com.bookstore.application;

import com.bookstore.adapter.input.dto.AuthorRequest;
import com.bookstore.core.domain.Author;
import com.bookstore.adapter.output.AuthorPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class AuthorUseCaseImplTest {

    @Test
    void testSaveAuthor() {
        // Arrange
        AuthorPersistencePort mockPersistencePort = Mockito.mock(AuthorPersistencePort.class);
        AuthorUseCaseImpl authorUseCase = new AuthorUseCaseImpl(mockPersistencePort);

        AuthorRequest authorRequest = new AuthorRequest(null, "John Doe");
        Author savedAuthor = new Author(1L, "John Doe");

        Mockito.when(mockPersistencePort.saveAuthor(any(Author.class))).thenReturn(savedAuthor);

        // Act
        Long result = authorUseCase.saveAuthor(authorRequest);

        // Assert
        assertEquals(1L, result);
        Mockito.verify(mockPersistencePort).saveAuthor(any(Author.class));
    }
}
