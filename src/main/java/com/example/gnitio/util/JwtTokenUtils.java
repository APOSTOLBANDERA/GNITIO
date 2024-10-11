package com.example.gnitio.util;


import com.example.gnitio.controller.UserController;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.codec.Hex;


import com.example.gnitio.controller.AuthController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;



@Component
public class JwtTokenUtils {

    private final Key secretKey;
    private final long jwtLifetime;

    Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    public JwtTokenUtils(
            @Value("${security.jwt.secret-key}") String secret,
            @Value("PT30000000m") String jwtLifetime) {
        // Проверяем, что ключ и время жизни не пустые
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key must not be empty");
        }
        if (jwtLifetime == null || jwtLifetime.isEmpty()) {
            throw new IllegalArgumentException("JWT lifetime must not be empty");
        }
        logger.info(" alaalx " + secret);
        // Создаем ключ из строки секрета
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        // Парсим время жизни токена
        this.jwtLifetime = Duration.parse(jwtLifetime).toMillis();
    }

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities());

        Date issuedDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(issuedDate.getTime() + jwtLifetime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .signWith(secretKey)                    // Указываем ключ для подписи
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        Date issuedDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(issuedDate.getTime() + jwtLifetime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .signWith(secretKey)                    // Указываем ключ для подписи
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)               // Устанавливаем ключ для проверки подписи
                .build()
                .parseClaimsJws(token)                  // Парсим токен и проверяем подпись
                .getBody();                             // Извлекаем клеймы из токена
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        logger.info("III5252" + claims.toString());
        return claims.getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("roles", List.class);     // Извлекаем роли из токена
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }
}
