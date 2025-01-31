package br.com.bookstore.adapter.configuration;

import br.com.bookstore.core.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;
    private final JwtUtil jwtUtil;

    public CustomJwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, JwtUtil jwtUtil) {
        this.resolver = resolver;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/bookstore-api/swagger-ui/")
                || requestURI.startsWith("/bookstore-api/v3/api-docs")
                || requestURI.equals("/bookstore-api/api-docs.yaml")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = request.getHeader("Authorization");
            if (token == null || !jwtUtil.validJwt(token)) {
                throw new JwtAuthenticationException();
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(jwtUtil.getEmail(token), null,
                            Collections.singletonList(new SimpleGrantedAuthority("SIMPLE_USER")));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            resolver.resolveException(request, response, null, exception);
        }
        
    }
}



