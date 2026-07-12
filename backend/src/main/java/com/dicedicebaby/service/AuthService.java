package com.dicedicebaby.service;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.request.*;
import com.dicedicebaby.dto.response.ConnectedPlayerResponseDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.repository.AccountRepository;
import com.dicedicebaby.repository.PlayerRepository;
import com.dicedicebaby.security.CookieUtils;
import com.dicedicebaby.security.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
  // region Attributes
  private final AccountService accountService;
  private final PlayerService playerService;
  private final AccountRepository accountRepository;
  private final PlayerRepository playerRepository;
  private final JwtUtils jwtUtils;
  private final CookieUtils cookieUtils;
  private final PasswordEncoder passwordEncoder;

  // endregion

  // region Constructor
  public AuthService(
      AccountService accountService,
      PlayerService playerService,
      AccountRepository accountRepository,
      PlayerRepository playerRepository,
      JwtUtils jwtUtils,
      CookieUtils cookieUtils,
      PasswordEncoder passwordEncoder) {
    this.accountService = accountService;
    this.playerService = playerService;
    this.accountRepository = accountRepository;
    this.playerRepository = playerRepository;
    this.jwtUtils = jwtUtils;
    this.cookieUtils = cookieUtils;
    this.passwordEncoder = passwordEncoder;
  }

  // endregion

  // region Methods
  /**
   * Returns the players connected through the current cookie.
   *
   * @param existingCookie the current session cookie
   * @return the connected players, or an empty list if no session exists
   */
  @Transactional
  public List<ConnectedPlayerResponseDTO> getCurrentSession(String existingCookie) {
    List<ConnectedPlayerResponseDTO> players = new ArrayList<>();

    // Return empty list of players if no one connected
    if (existingCookie == null || existingCookie.isEmpty()) {
      return players;
    }

    // Split tokens in a list
    String[] tokens = existingCookie.split(Constant.SEPARATOR);

    // Parse list to get players infos from database
    for (String token : tokens) {
      try {
        // Extract username from token via jwtUtils
        String username = jwtUtils.extractUsername(token);

        // Get player from database via repository
        PlayerEntity player = playerService.getPlayerByPlayerUsername(username);

        // Add player to players list
        players.add(
            new ConnectedPlayerResponseDTO(
                player.getId(),
                player.getPlayerUsername(),
                player.getIsGuest(),
                player.getPlayerNumber()));

      } catch (Exception e) {
        // If token is invalid or expired, ignore it
        System.out.println("Token invalide ignoré : " + e.getMessage());
      }
    }

    return players;
  }

  /**
   * Registers an account and adds its player to the session.
   *
   * @param request the registration data
   * @param response the HTTP response
   * @param existingCookie the current session cookie
   * @return the newly connected player
   * @throws IllegalStateException if the username or email is already used
   */
  @Transactional
  public ConnectedPlayerResponseDTO register(
      RegistrationRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Create account
    AccountEntity account =
        accountService.registerNewAccount(request.username(), request.email(), request.password());

    // Create player from account
    PlayerEntity player = playerService.createPlayerForAccount(account, request.playerNumber());

    // Generate and set current token to player
    String token = jwtUtils.generateToken(account.getUsername());
    player.setCurrentJwt(token);

    // Add new token in existing cookie
    cookieUtils.addTokenToCookie(token, existingCookie, response);

    // Return PlayerDTO
    return new ConnectedPlayerResponseDTO(
        player.getId(), player.getPlayerUsername(), player.getIsGuest(), player.getPlayerNumber());
  }

  /**
   * Authenticates a player and adds them to the session.
   *
   * @param request the login data
   * @param response the HTTP response
   * @param existingCookie the current session cookie
   * @return the connected player
   * @throws RuntimeException if the credentials are invalid
   */
  @Transactional
  public ConnectedPlayerResponseDTO login(
      LoginRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Check authentication
    AccountEntity account = accountService.getAccount(request.email(), request.password());

    // Get player from account
    PlayerEntity player = playerService.getPlayerByAccount(account);

    // Update player number
    player.setPlayerNumber(request.playerNumber());

    // Generate and update token to player
    String token = jwtUtils.generateToken(account.getUsername());
    player.setCurrentJwt(token);

    // Add new token in existing cookie
    cookieUtils.addTokenToCookie(token, existingCookie, response);

    // Return PlayerDTO
    return new ConnectedPlayerResponseDTO(
        player.getId(), player.getPlayerUsername(), player.getIsGuest(), player.getPlayerNumber());
  }

  /**
   * Removes a player from the current session.
   *
   * @param request the logout data
   * @param response the HTTP response
   * @param existingCookie the current session cookie
   */
  @Transactional
  public void logout(
      LogoutRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Try to find the right token to remove
    String tokenToRemove = null;
    try {
      tokenToRemove = jwtUtils.validateAndGetToken(request.username(), existingCookie);
    } catch (ResponseStatusException e) {
      System.out.println("Tentative de logout sur une session inexistante : " + e.getReason());
    }

    // Find player
    PlayerEntity player = playerRepository.findByPlayerUsername(request.username());

    // Delete player from database if guest (player with no account)
    if (player != null && player.getIsGuest()) {
      playerRepository.delete(player);
      System.out.println("Guest supprimé de la base : " + request.username());
    }

    // Delete player from the cookie if token found
    if (tokenToRemove != null) {
      cookieUtils.deleteTokenToCookie(tokenToRemove, existingCookie, response);
    }
  }

  /**
   * Deletes an account and removes its player from the session.
   *
   * @param request the account deletion data
   * @param response the HTTP response
   * @param existingCookie the current session cookie
   * @throws ResponseStatusException if authentication fails
   */
  @Transactional
  public void delete(
      DeleteRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Validate that token is found in cookie
    String token = jwtUtils.validateAndGetToken(request.username(), existingCookie);

    // Get account with username
    AccountEntity account = accountRepository.findByUsername(request.username());

    // Check passwords matching before deleting
    if (!passwordEncoder.matches(request.password(), account.getPasswordHash())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mot de passe incorrect");
    }

    // Delete linked player
    PlayerEntity player = playerService.getPlayerByAccount(account);
    playerRepository.delete(player);

    // Then delete account from database
    accountRepository.delete(account);

    // When it's done we delete it from the cookie
    cookieUtils.deleteTokenToCookie(token, existingCookie, response);
  }

  /**
   * Creates a guest player and adds them to the session.
   *
   * @param request the guest data
   * @param response the HTTP response
   * @param existingCookie the current session cookie
   * @return the newly connected guest
   */
  @Transactional
  public ConnectedPlayerResponseDTO guest(
      GuestRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Create player for a guest
    PlayerEntity player =
        playerService.createPlayerForGuest(request.username(), request.playerNumber());

    // Generate and set current token to player
    String token = jwtUtils.generateToken(request.username());
    player.setCurrentJwt(token);

    // Add new token in existing cookie
    cookieUtils.addTokenToCookie(token, existingCookie, response);

    return new ConnectedPlayerResponseDTO(
        player.getId(), player.getPlayerUsername(), player.getIsGuest(), player.getPlayerNumber());
  }

  /**
   * Updates the authenticated account and its player.
   *
   * @param request the updated account data
   * @param response the HTTP response
   * @param existingCookie the current session cookie
   * @return the updated player
   * @throws ResponseStatusException if authentication fails
   * @throws IllegalStateException if the username or email is already used
   */
  @Transactional
  public ConnectedPlayerResponseDTO update(
      UpdateRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Check authentication
    AccountEntity account =
        jwtUtils.getAccountFromCookies(request.currentPassword(), existingCookie);

    // Get player from account
    PlayerEntity player = playerService.getPlayerByAccount(account);

    // Validate that old token is found in cookie
    String oldToken = jwtUtils.validateAndGetToken(account.getUsername(), existingCookie);

    boolean usernameChanged = false;

    // Update Username & PlayerUsername
    if (request.username() != null
        && !request.username().isBlank()
        && !request.username().equals(account.getUsername())) {
      // Check if username is unique
      if (accountRepository.findByUsernameIgnoreCase(request.username()) != null) {
        throw new IllegalStateException("Ce pseudo est déjà pris.");
      }
      account.setUsername(request.username());
      player.setPlayerUsername(request.username());
      usernameChanged = true;
    }

    // Update Email
    if (request.email() != null
        && !request.email().isBlank()
        && !request.email().equals(account.getEmail())) {
      // Check if email is unique
      if (accountRepository.findByEmail(request.email()) != null) {
        throw new IllegalStateException("Cet email est déjà utilisé.");
      }
      account.setEmail(request.email());
    }

    // Update Password
    if (request.newPassword() != null && !request.newPassword().isBlank()) {
      account.setPasswordHash(passwordEncoder.encode(request.newPassword()));
    }

    accountRepository.save(account);

    // If username change, so change token from cookie
    if (usernameChanged) {
      String newToken = jwtUtils.generateToken(account.getUsername());
      player.setCurrentJwt(newToken);
      cookieUtils.updateTokenToCookie(oldToken, newToken, existingCookie, response);
    }

    playerRepository.save(player);

    // Return player
    return new ConnectedPlayerResponseDTO(
        player.getId(), player.getPlayerUsername(), player.getIsGuest(), player.getPlayerNumber());
  }
  // endregion
}
