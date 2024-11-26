package com.aman.esd_task.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTHelper {

    @Value("${jwt.secret}")
    String JWT_SECRET;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, String claimName, Class<T> type) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName, type);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(username, claims);
    }

    private String createToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .expiration(new Date(new Date().getTime() + 30 * 60 * 1000))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        return token != null && !isTokenExpired(token);
    }
    
    public String getToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer")) return null;
        try {
            return authHeader.substring(7);
        } catch(Exception e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public String getUsernameFromAuthHeader(String authHeader) {
        String token = getToken(authHeader);
        if (!validateToken(token)) throw new JwtException("Invalid token");
        return extractUsername(token);

    }
}
