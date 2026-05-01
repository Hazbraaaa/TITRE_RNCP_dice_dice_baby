package com.dicedicebaby.service;

import com.dicedicebaby.dto.response.CardResponseDTO;
import com.dicedicebaby.dto.response.DiceResponseDTO;
import com.dicedicebaby.dto.response.GameSetupResponseDTO;
import com.dicedicebaby.entity.DiceEntity;
import com.dicedicebaby.entity.DiceSetEntity;
import com.dicedicebaby.repository.CardRepository;
import com.dicedicebaby.repository.DiceSetRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameSetupService {
  // region Attributes
  private final CardRepository cardRepository;
  private final DiceSetRepository diceSetRepository;

  // endregion

  // region Constructor
  public GameSetupService(CardRepository cardRepository, DiceSetRepository diceSetRepository) {
    this.cardRepository = cardRepository;
    this.diceSetRepository = diceSetRepository;
  }

  // endregion

  // region Methods
  @Transactional
  public GameSetupResponseDTO setupNewGame() {
    // Get board game with random cards
    List<CardResponseDTO> board =
        cardRepository.findRandomCards().stream()
            .map(
                card ->
                    new CardResponseDTO(
                        card.getId(),
                        card.getCombination(),
                        card.getColor(),
                        card.getPointLvl1(),
                        card.getPointLvl2()))
            .toList();

    // Get dice set
    DiceSetEntity diceSet = new DiceSetEntity();
    List<DiceEntity> dices = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      DiceEntity dice = new DiceEntity();
      dice.setValue(1);
      dice.setKept(false);
      dice.setDiceSet(diceSet);
      dices.add(dice);
    }

    diceSet.setDices(dices);
    diceSetRepository.save(diceSet);

    return new GameSetupResponseDTO(
        board,
        diceSet.getId(),
        diceSet.getDices().stream().map(this::mapToDiceResponseDTO).toList());
  }

  private DiceResponseDTO mapToDiceResponseDTO(DiceEntity dice) {
    return new DiceResponseDTO(dice.getId(), dice.getValue(), dice.isKept());
  }
  // endregion
}
