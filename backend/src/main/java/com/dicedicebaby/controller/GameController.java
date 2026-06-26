package com.dicedicebaby.controller;

import com.dicedicebaby.dto.request.EndTurnRequestDTO;
import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.request.SkipTurnRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
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
  public GameResponseDTO roll(
      @RequestBody RollRequestDTO request,
      @CookieValue(name = "jwt_session", required = false) String existingCookie) {
    // Return updated game response after roll
    return gameService.rollDices(request);
  }

  @PostMapping("/end-turn")
  @ResponseStatus(HttpStatus.OK)
  public GameResponseDTO endTurn(
      @RequestBody EndTurnRequestDTO request,
      @CookieValue(name = "jwt_session", required = false) String existingCookie) {
    // Return updated game response after end turn
    return gameService.checkEndTurn(request);
  }

  @PostMapping("/skip-turn")
  @ResponseStatus(HttpStatus.OK)
  public GameResponseDTO skipTurn(
      @RequestBody SkipTurnRequestDTO request,
      @CookieValue(name = "jwt_session", required = false) String existingCookie) {
    // Return updated game response after end turn
    return gameService.skipTurn(request);
  }
  // endregion
}
