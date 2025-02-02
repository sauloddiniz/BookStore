//package br.com.bookstore.adapter.configuration;
//
//import br.com.bookstore.core.exception.JwtAuthenticationException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.Collections;
//
//@Component
//public class CustomJwtFilter implements WebFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public CustomJwtFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        final String requestURI = exchange.getRequest().getPath().toString();
//
//        if (requestURI.startsWith("/bookstore-api/swagger-ui/") ||
//                requestURI.startsWith("/bookstore-api/v3/api-docs") ||
//                requestURI.equals("/bookstore-api/api-docs.yaml")) {
//            return chain.filter(exchange);
//        }
//
//        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (token == null || !jwtUtil.validJwt(token)) {
//            return handleUnauthorized(exchange, new JwtAuthenticationException());
//        }
//
//        try {
//            String email = jwtUtil.getEmail(token);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    email, null, Collections.singletonList(new SimpleGrantedAuthority("SIMPLE_USER"))
//            );
//
//            return chain.filter(exchange)
//                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
//
//        } catch (Exception ex) {
//            return handleUnauthorized(exchange, ex);
//        }
//    }
//
//    private Mono<Void> handleUnauthorized(ServerWebExchange exchange, Throwable ex) {
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
//    }
//}
//
//
