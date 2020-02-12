package com.rmaj91.fishingstoreapi.security;

import com.rmaj91.fishingstoreapi.store.model.Role;
import com.rmaj91.fishingstoreapi.store.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${security.jwt.secretKey}")
    private String secretKey;
    @Value("${security.jwt.expirationTimeSec}")
    private String expirationTimeSec;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(User user) {
        List<Role> roles = Arrays.asList(user.getRole());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        Long expirationTimeSecLong = Long.parseLong(expirationTimeSec);
        final Date expiration = new Date(new Date().getTime() + expirationTimeSecLong * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public Boolean isTokenValid(String token) {
        final Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.after(new Date());
    }
}
