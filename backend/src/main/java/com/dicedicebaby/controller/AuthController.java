package com.dicedicebaby.controller;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.request.*;
import com.dicedicebaby.dto.response.ConnectedPlayerResponseDTO;
import com.dicedicebaby.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Account and session management")
@RestController
@RequestMapping("/auth")
public class AuthController {

  // region Attributes
  private final AuthService authService;

  // endregion

  // region Constructor
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  // endregion

  // region Routes
  @Operation(
      summary = "Get the current session",
      description = "Returns all players connected through the current session cookie.")
  @ApiResponse(responseCode = "200", description = "Session retrieved")
  @GetMapping("/session")
  @ResponseStatus(HttpStatus.OK)
  public List<ConnectedPlayerResponseDTO> getCurrentSession(
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    return authService.getCurrentSession(existingCookie);
  }

  @Operation(
      summary = "Register a new account",
      description = "Creates an account and adds its player to the current session.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Account created"),
    @ApiResponse(responseCode = "400", description = "Invalid registration data"),
    @ApiResponse(responseCode = "409", description = "Username or email already used")
  })
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public ConnectedPlayerResponseDTO register(
      @Valid @RequestBody RegistrationRequestDTO request,
      HttpServletResponse response,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    return authService.register(request, response, existingCookie);
  }

  @Operation(
      summary = "Log in",
      description = "Authenticates an account and adds its player to the current session.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Authentication successful"),
    @ApiResponse(responseCode = "400", description = "Invalid login data"),
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
  })
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public ConnectedPlayerResponseDTO login(
      @Valid @RequestBody LoginRequestDTO request,
      HttpServletResponse response,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    return authService.login(request, response, existingCookie);
  }

  @Operation(summary = "Log out", description = "Removes a player from the current session.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Player logged out"),
    @ApiResponse(responseCode = "400", description = "Invalid logout data")
  })
  @DeleteMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(
      @Valid @RequestBody LogoutRequestDTO request,
      HttpServletResponse response,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    authService.logout(request, response, existingCookie);
  }

  @Operation(
      summary = "Delete an account",
      description = "Deletes an account and removes its player from the session.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Account deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid deletion data"),
    @ApiResponse(responseCode = "401", description = "Authentication failed")
  })
  @DeleteMapping("/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @Valid @RequestBody DeleteRequestDTO request,
      HttpServletResponse response,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    authService.delete(request, response, existingCookie);
  }

  @Operation(
      summary = "Create a guest player",
      description = "Creates a guest player and adds them to the current session.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Guest player created"),
    @ApiResponse(responseCode = "400", description = "Invalid guest data"),
    @ApiResponse(responseCode = "409", description = "Username already used")
  })
  @PostMapping("/guest")
  @ResponseStatus(HttpStatus.CREATED)
  public ConnectedPlayerResponseDTO guest(
      @Valid @RequestBody GuestRequestDTO request,
      HttpServletResponse response,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    return authService.guest(request, response, existingCookie);
  }

  @Operation(
      summary = "Update an account",
      description = "Updates the authenticated account and its associated player.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Account updated"),
    @ApiResponse(responseCode = "400", description = "Invalid update data"),
    @ApiResponse(responseCode = "401", description = "Authentication failed"),
    @ApiResponse(responseCode = "409", description = "Username or email already used")
  })
  @PutMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public ConnectedPlayerResponseDTO update(
      @Valid @RequestBody UpdateRequestDTO request,
      HttpServletResponse response,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    return authService.update(request, response, existingCookie);
  }
  // endregion
}
