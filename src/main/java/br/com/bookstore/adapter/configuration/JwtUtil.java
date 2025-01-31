package br.com.bookstore.adapter.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private JwtUtil() {
    }

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.enabled}")
    private boolean isEnabled;

    public String createToken(String sub, String email) {
        return JWT.create()
                .withSubject(sub)
                .withClaim("email", email)
                .withClaim("ROLE", "SIMPLE_USER")
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validJwt(String token) {
        log.info("isValid jwt {}", isEnabled);
        if (!isEnabled) {
            return true;
        }
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
        if (!isEnabled) {
            return "email_fake@email_fake.com";
        }
        DecodedJWT decodedJWT = decode(token);
        return decodedJWT.getClaim("email").asString();
    }

    private DecodedJWT decode(String token) {
        token = token.substring(7);
        return JWT.decode(token);
    }

}
