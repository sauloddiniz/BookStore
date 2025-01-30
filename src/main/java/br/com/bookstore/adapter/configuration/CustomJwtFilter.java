package br.com.bookstore.adapter.configuration;

import br.com.bookstore.core.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class CustomJwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    public CustomJwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        log.debug("Processing request to path: {}", servletPath);

        if (servletPath.startsWith("/swagger-ui/") ||
                servletPath.startsWith("/v3/api-docs") ||
                servletPath.equals("/api-docs.yaml")) {
            log.debug("Request ignored for path: {}", servletPath);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = request.getHeader("Authorization");

            if (token == null || !JwtUtil.validJwt(token)) {
                log.warn("Invalid or missing JWT token for path: {}", servletPath);
                throw new JwtAuthenticationException();
            }

            log.debug("Valid JWT token for path: {}", servletPath);
            filterChain.doFilter(request, response);

        } catch (JwtAuthenticationException exception) {
            log.error("JWT authentication failed for path: {}", servletPath, exception);
            resolver.resolveException(request, response, null, exception);
        }
    }
}



