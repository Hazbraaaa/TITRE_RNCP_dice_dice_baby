package com.dicedicebaby.controller;

import com.dicedicebaby.dto.PlayerResponseDTO;
import com.dicedicebaby.dto.RegistrationRequestDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.service.AccountService;
import com.dicedicebaby.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //region Attributes
    private final AccountService accountService;
    private final PlayerService playerService;
    //endregion

    //region Constructor
    public AuthController(AccountService accountService, PlayerService playerService) {
        this.accountService = accountService;
        this.playerService = playerService;
    }
    //endregion

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDTO register(@RequestBody RegistrationRequestDTO request) {

        AccountEntity savedAccount = accountService.registerNewAccount(
                request.username(),
                request.email(),
                request.password()
        );

        PlayerEntity playerEntity = playerService.createPlayerForAccount(savedAccount);

        return new PlayerResponseDTO(
                playerEntity.getId(),
                playerEntity.getUsername(),
                playerEntity.getIsGuest(),
                savedAccount.getId()
        );
    }
}
