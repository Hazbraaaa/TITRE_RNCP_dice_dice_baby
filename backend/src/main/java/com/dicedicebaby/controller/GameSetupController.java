package com.dicedicebaby.controller;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.service.GameSetupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Game setup", description = "Game creation and initialization")
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
  @Operation(
      summary = "Create a new game",
      description = "Creates and initializes a game for the players in the current session.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Game created successfully"),
    @ApiResponse(responseCode = "400", description = "The session does not contain valid players"),
    @ApiResponse(responseCode = "401", description = "Session missing or invalid"),
    @ApiResponse(responseCode = "409", description = "Game cannot be created")
  })
  @PostMapping("/setup")
  @ResponseStatus(HttpStatus.CREATED)
  public GameResponseDTO setup(
      @CookieValue(name = Constant.COOKIE_NAME, required = false) String existingCookie) {
    // Return Setup
    return gameSetupService.setupNewGame(existingCookie);
  }
  // endregion
}
