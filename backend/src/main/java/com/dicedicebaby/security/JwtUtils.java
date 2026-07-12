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

  /**
   * Generates an authentication token for a user.
   *
   * @param username the authenticated username
   * @return the generated JWT
   */
  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setId(UUID.randomUUID().toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 86400000))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Extracts the username from a JWT.
   *
   * @param token the JWT to parse
   * @return the username stored in the token
   */
  public String extractUsername(String token) {
    Claims claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  /**
   * Finds a valid token for a user in the session cookie.
   *
   * @param username the username to search for
   * @param existingCookie the current session cookie
   * @return the matching token
   * @throws ResponseStatusException if no valid token is found
   */
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

  /**
   * Authenticates and returns an account from the session cookie.
   *
   * @param password the raw account password
   * @param existingCookie the current session cookie
   * @return the authenticated account
   * @throws ResponseStatusException if authentication fails
   */
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
