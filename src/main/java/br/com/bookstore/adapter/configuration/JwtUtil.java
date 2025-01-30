package br.com.bookstore.adapter.configuration;

import br.com.bookstore.core.exception.JwtAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private JwtUtil() {
    }

    @Value("${jwt.secret:default-secret}")
    private static String secret;

    public static boolean validJwt(String token) {
        try {
            token = token.substring(7);
            JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getEmail(String token) {
        DecodedJWT decodedJWT = decode(token);
        return decodedJWT.getClaim("email").asString();
    }

    private static DecodedJWT decode(String token) {
        token = token.substring(7);
        return JWT.decode(token);
    }

}
