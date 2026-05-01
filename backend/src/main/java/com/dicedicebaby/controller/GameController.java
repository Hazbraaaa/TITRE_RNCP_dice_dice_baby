package com.dicedicebaby.controller;

import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.response.RollResponseDTO;
import com.dicedicebaby.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {
  // region Attributes
  private final GameService gameService;

  // endregion

  // region Constructor
  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  // endregion

  // region Routes
  @PostMapping("/roll")
  @ResponseStatus(HttpStatus.OK)
  public RollResponseDTO roll(
      @RequestBody RollRequestDTO request,
      @CookieValue(name = "jwt_session", required = false) String existingCookie) {
    // Return new dice set after roll
    return gameService.rollDices(request);
  }
  // endregion
}
