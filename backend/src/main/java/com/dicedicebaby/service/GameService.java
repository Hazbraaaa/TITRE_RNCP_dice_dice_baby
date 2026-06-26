package com.dicedicebaby.service;

import com.dicedicebaby.dto.request.EndTurnRequestDTO;
import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.request.SkipTurnRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.*;
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

    // Add score of game card for current player
    currentPlayer.setScore(currentPlayer.getScore() + gameCard.getCard().getPointLvl1());

    // Withdraw a token for current player
    currentPlayer.setRemainingChips(currentPlayer.getRemainingChips() - 1);

    // Set new turn condition
    game = setNewTurn(game);

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
    game = setNewTurn(game);

    return gameMapper.mapToGameResponseDTO(game);
  }

  private GameEntity setNewTurn(GameEntity game) {
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

    return game;
  }
  // endregion
}
