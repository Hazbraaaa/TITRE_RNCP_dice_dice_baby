package com.dicedicebaby.service;

import com.dicedicebaby.dto.PlayerResponseDTO;
import com.dicedicebaby.dto.RegistrationRequestDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dicedicebaby.service.AccountService;
import com.dicedicebaby.service.PlayerService;

@Service
public class AuthService {
    //region Attributes
    private final AccountService accountService;
    private final PlayerService playerService;
    //endregion

    //region Constructor
    public AuthService(AccountService accountService, PlayerService playerService) {
        this.accountService = accountService;
        this.playerService = playerService;
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
        PlayerEntity player = playerService.createPlayerForAccount(account);

        // Return PlayerDTO
        return new PlayerResponseDTO(
                player.getId(),
                player.getPlayerUsername(),
                player.getIsGuest(),
                account.getId()
        );
    }
    //endregion
}
