package com.dicedicebaby.service;

import com.dicedicebaby.dto.request.EndTurnRequestDTO;
import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.DiceEntity;
import com.dicedicebaby.entity.DiceSetEntity;
import com.dicedicebaby.entity.GameEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.mapper.GameMapper;
import com.dicedicebaby.repository.DiceSetRepository;
import com.dicedicebaby.repository.GameRepository;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

  // region Attributes
  private final DiceSetRepository diceSetRepository;
  private final GameRepository gameRepository;
  private final Random random = new Random();
  private final GameMapper gameMapper;

  // endregion

  // region Constructor
  public GameService(
      DiceSetRepository diceSetRepository, GameRepository gameRepository, GameMapper gameMapper) {
    this.diceSetRepository = diceSetRepository;
    this.gameRepository = gameRepository;
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

    // TODO ------- Avoid cheating and rthrow all dices at first throw

    // Throw the dices
    for (DiceEntity dice : diceSet.getDices()) {
      // Check if dice should be kept or roll from payload
      boolean shouldBeKept = request.keptDiceIds().contains(dice.getId());
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
    DiceSetEntity diceSet =
        diceSetRepository
            .findById(request.diceSetId())
            .orElseThrow(() -> new RuntimeException("DiceSet introuvable"));

    // Get game from diceset id
    GameEntity game = diceSet.getGame();
    if (game == null) {
      throw new RuntimeException("Partie introuvable en base");
    }

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

    return gameMapper.mapToGameResponseDTO(game);
  }
  // endregion
}
