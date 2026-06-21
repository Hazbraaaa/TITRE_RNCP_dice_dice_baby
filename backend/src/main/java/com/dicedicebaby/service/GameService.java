package com.dicedicebaby.service;

import com.dicedicebaby.dto.request.EndTurnRequestDTO;
import com.dicedicebaby.dto.request.RollRequestDTO;
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
    DiceSetEntity diceSet =
        diceSetRepository
            .findById(request.diceSetId())
            .orElseThrow(() -> new RuntimeException("DiceSet introuvable"));

    // Get game from diceset id
    GameEntity game = diceSet.getGame();
    if (game == null) {
      throw new RuntimeException("Partie introuvable en base");
    }

    // Check if there is rolls left
    if (game.getRollsLeft() <= 0) {
      throw new RuntimeException("Plus de lancers disponibles pour ce tour");
    }

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
    // Get dice set from request
    DiceSetEntity diceSet =
        diceSetRepository
            .findById(request.diceSetId())
            .orElseThrow(() -> new RuntimeException("DiceSet introuvable"));

    // TODO --- envoyer l'id de la game ou la recuperer autrement que depuis le diceset
    // Get game from diceset id
    GameEntity game = diceSet.getGame();
    if (game == null) {
      throw new RuntimeException("Partie introuvable en base");
    }

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

    // region SET NEW TURN
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

    // Change current player for game
    game.setCurrentPlayer(nextPlayer);

    // Reset diceset to unkept dices
    if (diceSet.getDices() != null) {
      diceSet.getDices().forEach(dice -> dice.setKept(false));
    }

    // Reset rolls left to 3
    game.setRollsLeft(3);
    // endregion

    return gameMapper.mapToGameResponseDTO(game);
  }
  // endregion
}
