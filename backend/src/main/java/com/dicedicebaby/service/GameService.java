package com.dicedicebaby.service;

import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.DiceEntity;
import com.dicedicebaby.entity.DiceSetEntity;
import com.dicedicebaby.entity.GameEntity;
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
  // endregion
}
