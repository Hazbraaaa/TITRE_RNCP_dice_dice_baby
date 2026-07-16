package com.dicedicebaby.controller;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.request.EndTurnRequestDTO;
import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.request.SkipTurnRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Game", description = "Game turn and dice management")
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
  @Operation(
      summary = "Roll the dice",
      description = "Rolls the available dice and returns the updated game.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Dice rolled successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid roll request"),
    @ApiResponse(responseCode = "401", description = "Session missing or invalid"),
    @ApiResponse(responseCode = "403", description = "Session not authorized for this game"),
    @ApiResponse(responseCode = "404", description = "Game not found"),
    @ApiResponse(responseCode = "409", description = "Roll not allowed")
  })
  @PostMapping("/roll")
  @ResponseStatus(HttpStatus.OK)
  public GameResponseDTO roll(
      @RequestBody RollRequestDTO request,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    // Return updated game response after roll
    return gameService.rollDices(request, existingCookie);
  }

  @Operation(
      summary = "End the current turn",
      description = "Validates the selected card, assigns points and starts the next turn.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Turn ended successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid card or dice combination"),
    @ApiResponse(responseCode = "401", description = "Session missing or invalid"),
    @ApiResponse(responseCode = "403", description = "Session not authorized for this game"),
    @ApiResponse(responseCode = "404", description = "Game or card not found"),
    @ApiResponse(responseCode = "409", description = "Turn cannot be completed")
  })
  @PostMapping("/end-turn")
  @ResponseStatus(HttpStatus.OK)
  public GameResponseDTO endTurn(
      @RequestBody EndTurnRequestDTO request,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    // Return updated game response after end turn
    return gameService.checkEndTurn(request, existingCookie);
  }

  @Operation(summary = "Skip the current turn", description = "Skip the current player's turn.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Turn skipped successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid skip request"),
    @ApiResponse(responseCode = "401", description = "Session missing or invalid"),
    @ApiResponse(responseCode = "403", description = "Session not authorized for this game"),
    @ApiResponse(responseCode = "404", description = "Game not found"),
    @ApiResponse(responseCode = "409", description = "Turn cannot be skipped")
  })
  @PostMapping("/skip-turn")
  @ResponseStatus(HttpStatus.OK)
  public GameResponseDTO skipTurn(
      @RequestBody SkipTurnRequestDTO request,
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    // Return updated game response after skip turn
    return gameService.skipTurn(request, existingCookie);
  }

  @Operation(summary = "Leave the game", description = "Clears the game session cookie.")
  @ApiResponse(responseCode = "204", description = "Game left successfully")
  @DeleteMapping("/leave")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void leaveGame(HttpServletResponse response) {
    // Nothing return after leave game
    gameService.leaveGame(response);
  }
  // endregion
}
