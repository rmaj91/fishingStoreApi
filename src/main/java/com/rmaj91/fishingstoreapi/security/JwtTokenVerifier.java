package com.rmaj91.fishingstoreapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    private static final int BEARER_PLACEHOLDER = 7;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(BEARER_PLACEHOLDER);
        try {
            Claims claims = jwtUtil.getAllClaimsFromToken(token);

            String userEmail = claims.getSubject();
            List<String> roles = (List<String>) claims.get("roles");

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userEmail,
                    null,
                    roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }
    }
}
