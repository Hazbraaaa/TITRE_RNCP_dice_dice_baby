package com.dicedicebaby.controller;

import com.dicedicebaby.dto.response.GameSetupResponseDTO;
import com.dicedicebaby.service.GameSetupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameSetupController {
  // region Attributes
  private final GameSetupService gameSetupService;

  // endregion

  // region Constructor
  public GameSetupController(GameSetupService gameSetupService) {
    this.gameSetupService = gameSetupService;
  }

  // endregion

  // region Routes
  @GetMapping("/setup")
  @ResponseStatus(HttpStatus.CREATED)
  public GameSetupResponseDTO setup(
      @CookieValue(name = "jwt_session", required = false) String existingCookie) {
    // Return Setup
    return gameSetupService.setupNewGame();
  }
  // endregion
}
