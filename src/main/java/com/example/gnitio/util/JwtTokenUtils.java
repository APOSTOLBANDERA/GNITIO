package com.example.gnitio.util;


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

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtils {
    private final Key secret;
    private final long lifeTime;

    Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    public JwtTokenUtils(
            @Value("3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b") String secret,
            @Value("PT3000m") String lifeTime
    ){
        if (secret == null || secret.isEmpty()){
            throw new IllegalArgumentException("JWT key is empty");

        }
        if(lifeTime == null || lifeTime.isEmpty()){
            throw new IllegalArgumentException("Jwt lifeTime is empty");
        }

        this.secret = Keys.hmacShaKeyFor(secret.getBytes());
        this.lifeTime = Duration.parse(lifeTime).toMillis();
    }

    public String generateToken(UserDetails userDetails){
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities());

        Date issueDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(issueDate.getTime() + lifeTime);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
        logger.info(jwt);
        return jwt;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = extractAllClaims(token);
        if (claims != null) {
            logger.info("Extracted claims: " + claims);
            return claims.getSubject();
        } else {
            logger.error("Failed to extract username from token: " + token);
            return null;
        }
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Failed to extract claims from token: " + token, e);
            return null;
        }
    }



    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            // Извлекаем имя пользователя из токена
            String username = getUsernameFromToken(token);
            // Проверяем, совпадает ли имя пользователя из токена с данными пользователя
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            logger.debug("JWT token expired");
            return false;
        }
    }

}
