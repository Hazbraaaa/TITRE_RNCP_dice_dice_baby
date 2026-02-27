package com.dicedicebaby.controller;

import com.dicedicebaby.dto.*;
import com.dicedicebaby.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/session")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponseDTO> getCurrentSession(@CookieValue(name = "jwt_session", required = false) String existingCookie) {
        return authService.getCurrentSession(existingCookie);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDTO register(@RequestBody RegistrationRequestDTO request,
                                      HttpServletResponse response,
                                      @CookieValue(name = "jwt_session", required = false) String existingCookie) {
        return authService.register(request, response, existingCookie);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PlayerResponseDTO login(@RequestBody LoginRequestDTO request,
                                   HttpServletResponse response,
                                   @CookieValue(name = "jwt_session", required = false) String existingCookie) {
        return authService.login(request, response, existingCookie);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestBody LogoutRequestDTO request,
                       HttpServletResponse response,
                       @CookieValue(name = "jwt_session", required = false) String existingCookie) {
            authService.logout(request, response, existingCookie);
    }

    @PostMapping("/guest")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDTO guest(@RequestBody GuestRequestDTO request) {
        return authService.guest(request);
    }
    //endregion
}
