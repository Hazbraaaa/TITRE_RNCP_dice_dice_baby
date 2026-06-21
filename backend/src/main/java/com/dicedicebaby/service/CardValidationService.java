package com.dicedicebaby.service;

import com.dicedicebaby.entity.GameCardEntity;
import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.service.strategy.CombinationFactory;
import com.dicedicebaby.service.strategy.CombinationValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardValidationService {
  // region Attributes
  private final CombinationFactory combinationFactory;

  // endregion

  // region Constructor
  @Autowired
  public CardValidationService(CombinationFactory combinationFactory) {
    this.combinationFactory = combinationFactory;
    ;
  }

  // endregion

  // region Methods
  public boolean validateCard(List<Integer> diceValues, GameCardEntity gameCardEntity) {
    if (gameCardEntity == null || gameCardEntity.getCard() == null) {
      throw new IllegalArgumentException(
          "La carte à valider ou ses données ne peuvent pas être nulles.");
    }

    // Extract requirement from game card combination
    CardRequirement requirement = gameCardEntity.getCard().getCombination();

    // Find the right validator from combination factory with requirement
    CombinationValidator validator = combinationFactory.getValidator(requirement);

    // Run algorithm to check validity
    return validator.isValid(diceValues, requirement);
  }
  // endRegion
}
