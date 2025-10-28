package com.fidelin.clicker.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;               // 👈 IMPORTANTE
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;             // 👈 SecretKey, no Key genérico
    private final long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generate(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, Jwts.SIG.HS256)   // 👈 HS256 con SecretKey
                .compact();
    }

    public String validateAndGetSubject(String token) {
        return Jwts.parser()
                .verifyWith(key)                 // 👈 verifica con la misma SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
