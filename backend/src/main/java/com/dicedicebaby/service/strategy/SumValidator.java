package com.dicedicebaby.service.strategy;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SumValidator implements CombinationValidator {

  @Override
  public boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement) {
    if (diceValues == null || diceValues.size() < Constant.GameData.DICE_COUNT) {
      return false;
    }

    // Sum all dice values
    int totalSum = diceValues.stream().mapToInt(Integer::intValue).sum();

    // Check and return if sum validate requirement
    return switch (cardRequirement) {
      case SUM_12_13_14 -> totalSum >= 12 && totalSum <= 14;
      case SUM_21_22_23 -> totalSum >= 21 && totalSum <= 23;
      case LESS_THAN_9 -> totalSum <= 9;
      case GREATER_THAN_26 -> totalSum >= 26;
      default ->
          throw new IllegalArgumentException(
              "Requirement non supporté par ce validateur : " + cardRequirement);
    };
  }

  @Override
  public CombinationType getCombinationName() {
    return CombinationType.SUM;
  }
}
