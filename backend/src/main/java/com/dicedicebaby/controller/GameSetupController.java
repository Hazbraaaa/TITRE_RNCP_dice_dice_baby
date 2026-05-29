package com.dicedicebaby.controller;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.service.GameSetupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
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
  public GameResponseDTO setup(
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    // Return Setup
    return gameSetupService.setupNewGame(existingCookie);
  }
  // endregion
}
