package com.dicedicebaby.controller;

import com.dicedicebaby.dto.PlayerResponseDTO;
import com.dicedicebaby.dto.RegistrationRequestDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.service.AccountService;
import com.dicedicebaby.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
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

    //region Routes
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDTO register(@RequestBody RegistrationRequestDTO request) {

        //Create a new account in the database
        AccountEntity savedAccount = accountService.registerNewAccount(
                request.username(),
                request.email(),
                request.password()
        );

        //Create a new player in the database
        PlayerEntity playerEntity = playerService.createPlayerForAccount(savedAccount);

        //Return the player info with his account ID
        return new PlayerResponseDTO(
                playerEntity.getId(),
                playerEntity.getPlayerUsername(),
                playerEntity.getIsGuest(),
                savedAccount.getId()
        );
    }
    //endregion
}
