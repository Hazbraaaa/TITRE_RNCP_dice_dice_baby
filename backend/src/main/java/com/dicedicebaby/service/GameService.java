package com.dicedicebaby.service;

import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.response.DiceResponseDTO;
import com.dicedicebaby.dto.response.RollResponseDTO;
import com.dicedicebaby.entity.DiceEntity;
import com.dicedicebaby.entity.DiceSetEntity;
import com.dicedicebaby.repository.DiceSetRepository;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

  // region Attributes
  private final DiceSetRepository diceSetRepository;
  private final Random random = new Random();

  // endregion

  // region Constructor
  public GameService(DiceSetRepository diceSetRepository) {
    this.diceSetRepository = diceSetRepository;
  }

  // endregion

  // region Methods
  @Transactional
  public RollResponseDTO rollDices(RollRequestDTO request) {
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

    return new RollResponseDTO(
        diceSet.getId(), diceSet.getDices().stream().map(this::mapToDiceResponseDTO).toList());
  }

  private DiceResponseDTO mapToDiceResponseDTO(DiceEntity dice) {
    return new DiceResponseDTO(dice.getId(), dice.getValue(), dice.isKept());
  }
  // endregion
}
