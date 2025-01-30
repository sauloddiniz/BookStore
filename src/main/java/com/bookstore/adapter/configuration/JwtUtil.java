package com.bookstore.adapter.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    private JwtUtil() {
    }

    private static final String SECRET_KEY = "chave-de-seguranca";

    public static boolean validJwt(String token) {
        token = token.substring(7);
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY))
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
