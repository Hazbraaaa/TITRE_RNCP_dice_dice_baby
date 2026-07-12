package com.dicedicebaby.service.strategy;

import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;

public interface CombinationValidator {

  /**
   * Checks whether the dice satisfy a card requirement.
   *
   * @param diceValues the dice values
   * @param cardRequirement the requirement to validate
   * @return {@code true} if the requirement is satisfied
   * @throws IllegalArgumentException if the requirement is unsupported
   */
  boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement);

  /**
   * Returns the combination type handled by this validator.
   *
   * @return the supported combination type
   */
  CombinationType getCombinationName();
}
