package com.dicedicebaby.service;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.request.EndTurnRequestDTO;
import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.request.SkipTurnRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.*;
import com.dicedicebaby.enums.ApiErrorCode;
import com.dicedicebaby.enums.GameState;
import com.dicedicebaby.exception.ApiException;
import com.dicedicebaby.mapper.GameMapper;
import com.dicedicebaby.repository.GameCardRepository;
import com.dicedicebaby.repository.GameRepository;
import com.dicedicebaby.security.CookieUtils;
import com.dicedicebaby.security.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

  // region Attributes
  private final GameRepository gameRepository;
  private final GameCardRepository gameCardRepository;
  private final CardValidationService cardValidationService;
  private final Random random = new Random();
  private final GameMapper gameMapper;
  private final CookieUtils cookieUtils;
  private final JwtUtils jwtUtils;

  // endregion

  // region Constructor
  public GameService(
      GameRepository gameRepository,
      GameCardRepository gameCardRepository,
      CardValidationService cardValidationService,
      GameMapper gameMapper,
      CookieUtils cookieUtils,
      JwtUtils jwtUtils) {
    this.gameRepository = gameRepository;
    this.gameCardRepository = gameCardRepository;
    this.cardValidationService = cardValidationService;
    this.gameMapper = gameMapper;
    this.cookieUtils = cookieUtils;
    this.jwtUtils = jwtUtils;
  }

  // endregion

  // region Methods
  /**
   * Rolls the available dice and updates the game.
   *
   * @param request the roll data
   * @param existingCookie the session cookie
   * @return the updated game
   * @throws ApiException if authentication, authorization or business validation fails
   */
  @Transactional
  public GameResponseDTO rollDices(RollRequestDTO request, String existingCookie) {
    Set<String> authenticatedUsernames = jwtUtils.extractValidUsernames(existingCookie);

    // Get game from game id
    GameEntity game =
        gameRepository
            .findById(request.gameId())
            .orElseThrow(() -> new ApiException(ApiErrorCode.GAME_NOT_FOUND));

    verifySessionGame(game, authenticatedUsernames);

    // Check if there is rolls left
    if (game.getRollsLeft() <= Constant.GameData.NO_ROLLS_LEFT) {
      throw new ApiException(ApiErrorCode.NO_ROLL_LEFT);
    }

    // Get dice set from game
    DiceSetEntity diceSet = game.getDiceSet();

    // Throw the dices
    for (DiceEntity dice : diceSet.getDices()) {
      // Check if dice should be kept or roll from payload (kept impossible in first roll)
      boolean shouldBeKept =
          game.getRollsLeft() != Constant.GameData.MAX_ROLLS_LEFT
              && request.keptDiceIds().contains(dice.getId());
      dice.setKept(shouldBeKept);

      // Roll only the right dices
      if (!dice.isKept()) {
        dice.setValue(random.nextInt(Constant.GameData.MAX_DICE_VALUE) + 1);
      }
    }

    // Decrease number of rolls left
    game.setRollsLeft(game.getRollsLeft() - 1);

    return gameMapper.mapToGameResponseDTO(game);
  }

  /**
   * Validates the selected card and ends the current turn.
   *
   * @param request the end-turn data
   * @param existingCookie the session cookie
   * @return the updated game
   * @throws ApiException if authentication, authorization or business validation fails
   */
  @Transactional
  public GameResponseDTO checkEndTurn(EndTurnRequestDTO request, String existingCookie) {
    Set<String> authenticatedUsernames = jwtUtils.extractValidUsernames(existingCookie);

    // Get game from game id
    GameEntity game =
        gameRepository
            .findById(request.gameId())
            .orElseThrow(() -> new ApiException(ApiErrorCode.GAME_NOT_FOUND));

    verifySessionGame(game, authenticatedUsernames);

    // Get dice set from game
    DiceSetEntity diceSet = game.getDiceSet();

    // Get game card from request
    GameCardEntity gameCard =
        gameCardRepository
            .findById(request.gameCardId())
            .orElseThrow(() -> new ApiException(ApiErrorCode.CARD_NOT_FOUND));

    // region CHECK CARD VALIDATION
    // Set dice set combination in list of integer for validity check
    List<Integer> playerDiceValues = diceSet.getDices().stream().map(DiceEntity::getValue).toList();

    // Check valid turn with card validation service
    boolean isValidTurn = cardValidationService.validateCard(playerDiceValues, gameCard);
    // endRegion

    // If not valid turn return same game state
    if (!isValidTurn) {
      throw new ApiException(ApiErrorCode.CARD_VALIDATION_FAILED);
    }

    // Get current player
    PlayerEntity currentPlayer = game.getCurrentPlayer();

    // Assign points, owner slot and handle business rules
    assignCardPointsAndOwner(currentPlayer, gameCard);

    // Withdraw a token for current player
    currentPlayer.setRemainingChips(currentPlayer.getRemainingChips() - 1);

    // Update game if where is a winner and game is finished
    updateGameStateAndWinner(game);

    // If game state is still in progress
    if (game.getState() == GameState.IN_PROGRESS) {
      // Set new turn condition
      setNewTurn(game);
    }

    return gameMapper.mapToGameResponseDTO(game);
  }

  /**
   * Skips the current player's turn.
   *
   * @param request the skip-turn data
   * @return the updated game
   * @throws ApiException if authentication, authorization or business validation fails
   */
  @Transactional
  public GameResponseDTO skipTurn(SkipTurnRequestDTO request, String existingCookie) {
    Set<String> authenticatedUsernames = jwtUtils.extractValidUsernames(existingCookie);

    // Get game from game id
    GameEntity game =
        gameRepository
            .findById(request.gameId())
            .orElseThrow(() -> new ApiException(ApiErrorCode.GAME_NOT_FOUND));

    verifySessionGame(game, authenticatedUsernames);

    // Set new turn condition
    setNewTurn(game);

    return gameMapper.mapToGameResponseDTO(game);
  }

  /**
   * Removes the current game session cookie.
   *
   * @param response the HTTP response
   */
  @Transactional
  public void leaveGame(HttpServletResponse response) {
    // Clear the cookie
    cookieUtils.clearCookie(response);

    // TODO ------- More actions to clean the database, archive game, etc...
  }

  private void verifySessionGame(GameEntity game, Set<String> authenticatedUsernames) {
    boolean containsAllPlayers =
        game.getPlayers().stream()
            .map(PlayerEntity::getPlayerUsername)
            .map(String::toLowerCase)
            .allMatch(authenticatedUsernames::contains);

    if (!containsAllPlayers) {
      throw new ApiException(ApiErrorCode.GAME_ACCESS_DENIED);
    }

    if (game.getState() == GameState.FINISHED) {
      throw new ApiException(ApiErrorCode.GAME_ALREADY_FINISHED);
    }
  }

  private void updateGameStateAndWinner(GameEntity game) {
    // Get current player
    PlayerEntity currentPlayer = game.getCurrentPlayer();

    // Get conditions of victory
    boolean hasNoChipsLeft = currentPlayer.getRemainingChips() == Constant.GameData.NO_CHIPS_LEFT;
    boolean hasReachedTargetScore = currentPlayer.getScore() >= Constant.GameData.WINNING_POINTS;

    // If one condition is check set winner and game as finished
    if (hasNoChipsLeft || hasReachedTargetScore) {
      game.setState(GameState.FINISHED);
      game.setWinner(currentPlayer);
    }
    // Else keep game in progress
    else {
      game.setState(GameState.IN_PROGRESS);
    }
  }

  private void setNewTurn(GameEntity game) {
    // Get total players for game
    int totalPlayers = game.getPlayers().size();

    // Get next player number || Explanation : (1 % 4) + 1 = 2, ..., (4 % 4) + 1 = 1
    int nextPlayerNumber = (game.getCurrentPlayer().getPlayerNumber() % totalPlayers) + 1;

    // Find next player with player number
    PlayerEntity nextPlayer =
        game.getPlayers().stream()
            .filter(p -> p.getPlayerNumber() == nextPlayerNumber)
            .findFirst()
            .orElseThrow(() -> new ApiException(ApiErrorCode.NEXT_PLAYER_NOT_FOUND));

    // If next player number is one it's the end of the round, so increase round number
    if (nextPlayerNumber == Constant.GameData.FIRST_PLAYER_NUMBER) {
      game.setRoundNumber(game.getRoundNumber() + 1);
    }

    // Change current player for game
    game.setCurrentPlayer(nextPlayer);

    // Get dice set of game
    DiceSetEntity diceSet = game.getDiceSet();

    // Reset dice set to unkept dices
    if (diceSet.getDices() != null) {
      diceSet.getDices().forEach(dice -> dice.setKept(false));
    }

    // Reset rolls left to 3
    game.setRollsLeft(Constant.GameData.MAX_ROLLS_LEFT);
  }

  private void assignCardPointsAndOwner(PlayerEntity currentPlayer, GameCardEntity gameCard) {
    if (currentPlayer.equals(gameCard.getOwnerPointLvl1())
        || currentPlayer.equals(gameCard.getOwnerPointLvl2())) {
      throw new ApiException(ApiErrorCode.CARD_ALREADY_OWNED);
    }

    // Add score and make current player owner of the right level point for this game card
    if (gameCard.getOwnerPointLvl1() == null) {
      gameCard.setOwnerPointLvl1(currentPlayer);
      currentPlayer.setScore(currentPlayer.getScore() + gameCard.getCard().getPointLvl1());
    } else if (gameCard.getOwnerPointLvl2() == null) {
      gameCard.setOwnerPointLvl2(currentPlayer);
      currentPlayer.setScore(currentPlayer.getScore() + gameCard.getCard().getPointLvl2());
    } else {
      throw new ApiException(ApiErrorCode.CARD_FULLY_CLAIMED);
    }
  }
  // endregion
}
