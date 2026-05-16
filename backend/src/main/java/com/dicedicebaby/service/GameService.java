package com.dicedicebaby.service;

import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.DiceEntity;
import com.dicedicebaby.entity.DiceSetEntity;
import com.dicedicebaby.entity.GameEntity;
import com.dicedicebaby.mapper.GameMapper;
import com.dicedicebaby.repository.DiceSetRepository;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

  // region Attributes
  private final DiceSetRepository diceSetRepository;
  private final Random random = new Random();
  private final GameMapper gameMapper;

  // endregion

  // region Constructor
  public GameService(DiceSetRepository diceSetRepository, GameMapper gameMapper) {
    this.diceSetRepository = diceSetRepository;
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

    for (DiceEntity dice : diceSet.getDices()) {
      // Check if dice should be kept or roll from payload
      boolean shouldBeKept = request.keptDiceIds().contains(dice.getId());
      dice.setKept(shouldBeKept);

      // Roll only the right dices
      if (!dice.isKept()) {
        dice.setValue(random.nextInt(6) + 1);
      }
    }
    diceSetRepository.save(diceSet);

    // Get game from diceset id
    GameEntity game = diceSet.getGame();
    if (game == null) {
      throw new RuntimeException("Aucune partie associée à ce set de dés");
    }

    return gameMapper.mapToGameResponseDTO(game);
  }
  // endregion
}
