package br.com.bookstore.adapter.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private JwtUtil() {
    }

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(String sub, String email) {
        return JWT.create()
                .withSubject(sub)
                .withClaim("email", email)
                .withClaim("ROLE", "SIMPLE_USER")
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validJwt(String token) {
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

    public String getEmail(String token) {
        DecodedJWT decodedJWT = decode(token);
        return decodedJWT.getClaim("email").asString();
    }

    private DecodedJWT decode(String token) {
        token = token.substring(7);
        return JWT.decode(token);
    }

}
