package com.dicedicebaby.service;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.*;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.security.JwtUtils;
import com.dicedicebaby.security.CookieUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    //region Attributes
    private final AccountService accountService;
    private final PlayerService playerService;
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    //endregion

    //region Constructor
    public AuthService(AccountService accountService, PlayerService playerService, JwtUtils jwtUtils, CookieUtils cookieUtils) {
        this.accountService = accountService;
        this.playerService = playerService;
        this.jwtUtils = jwtUtils;
        this.cookieUtils = cookieUtils;
    }
    //endregion

    //region Methods
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
                players.add(new PlayerResponseDTO(
                        player.getId(),
                        player.getPlayerUsername(),
                        player.getIsGuest(),
                        player.getPlayerNumber(),
                        player.getScore()
                ));

            } catch (Exception e) {
                // If token is invalid or expired, ignore it
                System.out.println("Token invalide ignoré : " + e.getMessage());
            }
        }

        return players;
    }

    @Transactional
    public PlayerResponseDTO register(RegistrationRequestDTO request, HttpServletResponse response, String existingCookie) {
        // Create account
        AccountEntity account = accountService.registerNewAccount(
                request.username(),
                request.email(),
                request.password()
        );

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
                player.getScore()
        );
    }

    @Transactional
    public PlayerResponseDTO login(LoginRequestDTO request, HttpServletResponse response, String existingCookie) {
        // Check authentication
        AccountEntity account = accountService.loginAccount(
                request.email(),
                request.password()
        );

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
                player.getScore()
        );
    }

    @Transactional
    public void logout(LogoutRequestDTO request, HttpServletResponse response, String existingCookie) {
        // Return if none or empty cookie
        if (existingCookie == null || existingCookie.isEmpty()) {
            return;
        }

        //
        String[] tokens = existingCookie.split(Constant.SEPARATOR);
        String tokenToRemove = null;

        // Parse list to get players infos from database
        for (String token : tokens) {
            try {
                // Get usernames to compare
                String extractedUsernameFromToken = jwtUtils.extractUsername(token);
                String extractedUsernameFromCookie = request.username();

                // Compare username from token to cookie
                if (extractedUsernameFromToken.equals(extractedUsernameFromCookie)) {
                    tokenToRemove = token;
                    break;
                }
            } catch (Exception e) {
                // If token is invalid or expired, ignore it
                System.out.println("Erreur lors de l'analyse d'un token : " + e.getMessage());
            }
        }

        // If token found, delete it from cookie
        if (tokenToRemove != null) {
            cookieUtils.deleteTokenToCookie(tokenToRemove, existingCookie, response);
        }
    }

    @Transactional
    public PlayerResponseDTO guest(GuestRequestDTO request) {
        // Create player for a guest
        PlayerEntity player = playerService.createPlayerForGuest(request.username(), request.playerNumber());

        return new PlayerResponseDTO(
                player.getId(),
                player.getPlayerUsername(),
                player.getIsGuest(),
                player.getPlayerNumber(),
                player.getScore()
        );
    }
    //endregion
}
