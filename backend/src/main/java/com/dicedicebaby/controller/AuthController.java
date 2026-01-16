package com.dicedicebaby.controller;

import com.dicedicebaby.dto.LoginRequestDTO;
import com.dicedicebaby.dto.PlayerResponseDTO;
import com.dicedicebaby.dto.RegistrationRequestDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.service.AuthService;
import com.dicedicebaby.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //region Attributes
    private final AuthService authService;
    //endregion

    //region Constructor
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //endregion

    //region Routes
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDTO register(@RequestBody RegistrationRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PlayerResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
    //endregion
}
