package br.com.bookstore.adapter.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secret", "secret");
        ReflectionTestUtils.setField(jwtUtil, "isEnabled", true);
    }

    @Test
    @DisplayName("Should return true for a valid JWT")
    void shouldValidateJwtSuccessfully() {

        String jwtToken = jwtUtil.createToken("sub", "email");
        jwtToken = "Bearer " + jwtToken;

        boolean isValid = jwtUtil.validJwt(jwtToken);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return true for a invalid JWT")
    void shouldInvalidateJwtSuccessfully() {

        String jwtToken = "jwtTokenFake";
        jwtToken = "Bearer " + jwtToken;

        boolean isValid = jwtUtil.validJwt(jwtToken);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should extract email from a valid JWT")
    void shouldExtractEmailFromJwt() {

        String expectedEmail = "email@example.com";

        String jwtToken = jwtUtil.createToken("sub", expectedEmail);
        jwtToken = "Bearer " + jwtToken;

        String extractedEmail = jwtUtil.getEmail(jwtToken);

        assertEquals(expectedEmail, extractedEmail, "The extracted email should match the expected email.");
    }


}
