package com.dicedicebaby.service.strategy;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ParityValidator implements CombinationValidator {

  /** {@inheritDoc} */
  @Override
  public boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement) {
    if (diceValues == null || diceValues.size() < Constant.GameData.DICE_COUNT) {
      return false;
    }

    // Check and return if parity validate requirement
    return switch (cardRequirement) {
      case ALL_EVEN -> diceValues.stream().allMatch(value -> value % 2 == 0);
      case ALL_ODD -> diceValues.stream().allMatch(value -> value % 2 != 0);
      default ->
          throw new IllegalArgumentException(
              "Requirement non supporté par ce validateur : " + cardRequirement);
    };
  }

  /** {@inheritDoc} */
  @Override
  public CombinationType getCombinationName() {
    return CombinationType.PARITY;
  }
}
