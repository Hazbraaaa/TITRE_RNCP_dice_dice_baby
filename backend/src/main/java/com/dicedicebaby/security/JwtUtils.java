package com.dicedicebaby.security;

import com.dicedicebaby.config.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String secretKey;

  // Transform string into key
  private Key getSigningKey() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Generate unique token
  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setId(UUID.randomUUID().toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 86400000))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  // Extract username from token
  public String extractUsername(String token) {
    Claims claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  // Validate and get token from cookie
  public String validateAndGetToken(String username, String existingCookie) {
    if (existingCookie == null || existingCookie.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session absente");
    }

    String[] tokens = existingCookie.split(Constant.SEPARATOR);
    for (String token : tokens) {
      try {
        if (extractUsername(token).equals(username)) {
          return token;
        }
      } catch (Exception e) {
        continue;
      }
    }
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalide pour ce joueur");
  }
}
