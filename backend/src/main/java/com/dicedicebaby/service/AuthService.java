package com.dicedicebaby.service;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.*;
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
  @Transactional
  public List<PlayerResponseDTO> getCurrentSession(String existingCookie) {
    List<PlayerResponseDTO> players = new ArrayList<>();

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
            new PlayerResponseDTO(
                player.getId(),
                player.getPlayerUsername(),
                player.getIsGuest(),
                player.getPlayerNumber(),
                player.getScore()));

      } catch (Exception e) {
        // If token is invalid or expired, ignore it
        System.out.println("Token invalide ignoré : " + e.getMessage());
      }
    }

    return players;
  }

  @Transactional
  public PlayerResponseDTO register(
      RegistrationRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Create account
    AccountEntity account =
        accountService.registerNewAccount(request.username(), request.email(), request.password());

    // Create player from account
    PlayerEntity player = playerService.createPlayerForAccount(account, request.playerNumber());

    // Generate and set current token to player
    String token = jwtUtils.generateToken(account.getUsername());
    player.setCurrentToken(token);

    // Add new token in existing cookie
    cookieUtils.addTokenToCookie(token, existingCookie, response);

    // Return PlayerDTO
    return new PlayerResponseDTO(
        player.getId(),
        player.getPlayerUsername(),
        player.getIsGuest(),
        player.getPlayerNumber(),
        player.getScore());
  }

  @Transactional
  public PlayerResponseDTO login(
      LoginRequestDTO request, HttpServletResponse response, String existingCookie) {
    // Check authentication
    AccountEntity account = accountService.loginAccount(request.email(), request.password());

    // Get player from account
    PlayerEntity player = playerService.getPlayerByAccount(account);

    // Update player number
    player.setPlayerNumber(request.playerNumber());

    // Generate and update token to player
    String token = jwtUtils.generateToken(account.getUsername());
    player.setCurrentToken(token);

    // Add new token in existing cookie
    cookieUtils.addTokenToCookie(token, existingCookie, response);

    // Return PlayerDTO
    return new PlayerResponseDTO(
        player.getId(),
        player.getPlayerUsername(),
        player.getIsGuest(),
        player.getPlayerNumber(),
        player.getScore());
  }

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

  @Transactional
  public PlayerResponseDTO guest(GuestRequestDTO request) {
    // Create player for a guest
    PlayerEntity player =
        playerService.createPlayerForGuest(request.username(), request.playerNumber());

    return new PlayerResponseDTO(
        player.getId(),
        player.getPlayerUsername(),
        player.getIsGuest(),
        player.getPlayerNumber(),
        player.getScore());
  }
  // endregion
}
