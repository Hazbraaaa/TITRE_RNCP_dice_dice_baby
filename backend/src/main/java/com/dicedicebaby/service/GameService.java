package com.dicedicebaby.service;

import com.dicedicebaby.dto.request.EndTurnRequestDTO;
import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.request.SkipTurnRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.*;
import com.dicedicebaby.enums.GameState;
import com.dicedicebaby.mapper.GameMapper;
import com.dicedicebaby.repository.DiceSetRepository;
import com.dicedicebaby.repository.GameCardRepository;
import com.dicedicebaby.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

  // region Attributes
  private final DiceSetRepository diceSetRepository;
  private final GameRepository gameRepository;
  private final GameCardRepository gameCardRepository;
  private final CardValidationService cardValidationService;
  private final Random random = new Random();
  private final GameMapper gameMapper;

  // endregion

  // region Constructor
  public GameService(
      DiceSetRepository diceSetRepository,
      GameRepository gameRepository,
      GameCardRepository gameCardRepository,
      CardValidationService cardValidationService,
      GameMapper gameMapper) {
    this.diceSetRepository = diceSetRepository;
    this.gameRepository = gameRepository;
    this.gameCardRepository = gameCardRepository;
    this.cardValidationService = cardValidationService;
    this.gameMapper = gameMapper;
  }

  // endregion

  // region Methods

  @Transactional
  public GameResponseDTO rollDices(RollRequestDTO request) {
    // Get game from game id
    GameEntity game =
        gameRepository
            .findById(request.gameId())
            .orElseThrow(() -> new EntityNotFoundException("Cette partie n'existe pas"));

    // Check if there is rolls left
    if (game.getRollsLeft() <= 0) {
      throw new RuntimeException("Plus de lancers disponibles pour ce tour");
    }

    // Get dice set from game
    DiceSetEntity diceSet = game.getDiceSet();

    // Throw the dices
    for (DiceEntity dice : diceSet.getDices()) {
      // Check if dice should be kept or roll from payload (kept impossible in first roll)
      boolean shouldBeKept =
          game.getRollsLeft() != 3 && request.keptDiceIds().contains(dice.getId());
      dice.setKept(shouldBeKept);

      // Roll only the right dices
      if (!dice.isKept()) {
        dice.setValue(random.nextInt(6) + 1);
      }
    }

    // Decrease number of rolls left
    game.setRollsLeft(game.getRollsLeft() - 1);

    return gameMapper.mapToGameResponseDTO(game);
  }

  @Transactional
  public GameResponseDTO checkEndTurn(EndTurnRequestDTO request) {
    // Get game from game id
    GameEntity game =
        gameRepository
            .findById(request.gameId())
            .orElseThrow(() -> new EntityNotFoundException("Cette partie n'existe pas"));

    // Get dice set from game
    DiceSetEntity diceSet = game.getDiceSet();

    // Get game card from request
    GameCardEntity gameCard =
        gameCardRepository
            .findById(request.gameCardId())
            .orElseThrow(() -> new EntityNotFoundException("Cette carte n'existe pas"));

    // region CHECK CARD VALIDATION
    // Set dice set combination in list of integer for validity check
    List<Integer> playerDiceValues = diceSet.getDices().stream().map(DiceEntity::getValue).toList();

    // Check valid turn with card validation service
    boolean isValidTurn = cardValidationService.validateCard(playerDiceValues, gameCard);
    // endRegion

    // If not valid turn return same game state
    if (!isValidTurn) {
      throw new IllegalArgumentException("Les dés ne correspondent pas aux exigences de la carte.");
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

  @Transactional
  public GameResponseDTO skipTurn(SkipTurnRequestDTO request) {
    // Get game from game id
    GameEntity game =
        gameRepository
            .findById(request.gameId())
            .orElseThrow(() -> new EntityNotFoundException("Cette partie n'existe pas"));

    // Set new turn condition
    setNewTurn(game);

    return gameMapper.mapToGameResponseDTO(game);
  }

  private void updateGameStateAndWinner(GameEntity game) {
    // Get current player
    PlayerEntity currentPlayer = game.getCurrentPlayer();

    // Get conditions of victory
    boolean hasNoChipsLeft = currentPlayer.getRemainingChips() == 0;
    boolean hasReachedTargetScore = currentPlayer.getScore() >= 15;

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
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Joueur suivant introuvable pour le numéro : " + nextPlayerNumber));

    // If next player number is one it's the end of the round, so increase round number
    if (nextPlayerNumber == 1) {
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
    game.setRollsLeft(3);
  }

  private void assignCardPointsAndOwner(PlayerEntity currentPlayer, GameCardEntity gameCard) {
    if (currentPlayer.equals(gameCard.getOwnerPointLvl1())
        || currentPlayer.equals(gameCard.getOwnerPointLvl2())) {
      throw new IllegalArgumentException("Vous avez déjà validé cette carte.");
    }

    // Add score and make current player owner of the right level point for this game card
    if (gameCard.getOwnerPointLvl1() == null) {
      gameCard.setOwnerPointLvl1(currentPlayer);
      currentPlayer.setScore(currentPlayer.getScore() + gameCard.getCard().getPointLvl1());
    } else if (gameCard.getOwnerPointLvl2() == null) {
      gameCard.setOwnerPointLvl2(currentPlayer);
      currentPlayer.setScore(currentPlayer.getScore() + gameCard.getCard().getPointLvl2());
    } else {
      throw new IllegalArgumentException("Cette carte est déjà entièrement prise.");
    }
  }
  // endregion
}
