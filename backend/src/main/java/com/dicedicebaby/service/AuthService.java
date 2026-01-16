package com.dicedicebaby.service;

import com.dicedicebaby.dto.LoginRequestDTO;
import com.dicedicebaby.dto.PlayerResponseDTO;
import com.dicedicebaby.dto.RegistrationRequestDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.security.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    //region Attributes
    private final AccountService accountService;
    private final PlayerService playerService;
    private final JwtUtils jwtUtils;
    //endregion

    //region Constructor
    public AuthService(AccountService accountService, PlayerService playerService, JwtUtils jwtUtils) {
        this.accountService = accountService;
        this.playerService = playerService;
        this.jwtUtils = jwtUtils;
    }
    //endregion

    //region Methods
    @Transactional
    public PlayerResponseDTO register(RegistrationRequestDTO request) {
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

        // Return PlayerDTO
        return new PlayerResponseDTO(
                player.getId(),
                player.getPlayerUsername(),
                player.getIsGuest(),
                account.getId(),
                player.getPlayerNumber(),
                player.getScore(),
                token
        );
    }

    @Transactional(readOnly = true)
    public PlayerResponseDTO login(LoginRequestDTO request) {
        // Check authentication
        AccountEntity account = accountService.loginAccount(
                request.email(),
                request.password()
        );

        // Get player from account
        PlayerEntity player = playerService.getPlayerByAccount(account,request.playerNumber());

        // Generate and set current token to player
        String token = jwtUtils.generateToken(account.getUsername());
        player.setCurrentToken(token);

        // Return PlayerDTO
        return new PlayerResponseDTO(
                player.getId(),
                player.getPlayerUsername(),
                player.getIsGuest(),
                account.getId(),
                player.getPlayerNumber(),
                player.getScore(),
                token
        );
    }
    //endregion
}
