package br.com.bookstore.adapter.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JwtUtilTest {


    @Test
    void validJwt_shouldReturnFalse_whenTokenIsInvalid() {

        String invalidToken = "Bearer invalid-token";

        boolean result = JwtUtil.validJwt(invalidToken);

        Assertions.assertFalse(result, "The validJwt method should return false for an invalid token.");
    }

    @Test
    void validJwt_shouldReturnFalse_whenTokenIsMalformed() {

        String malformedToken = "Malform";

        boolean result = JwtUtil.validJwt(malformedToken);

        Assertions.assertFalse(result, "The validJwt method should return false for a malformed token.");
    }

    @Test
    void validJwt_shouldReturnFalse_whenTokenIsExpired() {
        String secretKey = "default-secret";
        String expiredToken = "Bearer " + JWT.create()
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() - 1000))
                .sign(Algorithm.HMAC256(secretKey));

        boolean result = JwtUtil.validJwt(expiredToken);

        Assertions.assertFalse(result, "The validJwt method should return false for an expired token.");
    }

    @Test
    void validJwt_shouldReturnFalse_whenTokenSignatureIsInvalid() {
        String secretKey = "default-secret";
        String anotherSecretKey = "different-secret";
        String invalidSignatureToken = "Bearer " + JWT.create()
                .sign(Algorithm.HMAC256(anotherSecretKey));

        boolean result = JwtUtil.validJwt(invalidSignatureToken);

        Assertions.assertFalse(result, "The validJwt method should return false for a token with an invalid signature.");
    }
}
