package com.dicedicebaby.security;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.repository.AccountRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtUtils {

  // region Attributes
  @Value("${jwt.secret}")
  private String secretKey;

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  // endregion

  // region Constructor
  public JwtUtils(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // endregion

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

  // Get account with given password from cookie
  public AccountEntity getAccountFromCookies(String password, String existingCookie) {
    if (existingCookie == null || existingCookie.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session absente");
    }

    String[] tokens = existingCookie.split(Constant.SEPARATOR);
    for (String token : tokens) {
      try {
        String username = extractUsername(token);

        AccountEntity account = accountRepository.findByUsernameIgnoreCase(username);

        if (account != null && passwordEncoder.matches(password, account.getPasswordHash())) {
          return account;
        }
      } catch (Exception e) {
        continue;
      }
    }
    throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED, "Mot de passe incorrect ou session expirée");
  }
}
