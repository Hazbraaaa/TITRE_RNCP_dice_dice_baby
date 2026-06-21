package com.dicedicebaby.service.strategy;

import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class UniformityValidator implements CombinationValidator {

  @Override
  public boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement) {
    if (diceValues == null || diceValues.size() < 5) {
      return false;
    }

    // Check and return if uniformity validate requirement
    return switch (cardRequirement) {
        // yield = break with a returned value (boolean in this case)
      case ALL_IDENTICAL -> {
        // Get first dice value
        int firstDice = diceValues.getFirst();
        // Check that all dice values are equal to the first one
        yield diceValues.stream().allMatch(value -> value == firstDice);
      }
      case ALL_DIFFERENT -> {
        // Dice values put in a set that delete double
        Set<Integer> uniqueValues = new HashSet<>(diceValues);
        // If no double, unique values equal to number of dice
        yield uniqueValues.size() == 5;
      }
      default ->
          throw new IllegalArgumentException(
              "Requirement non supporté par ce validateur : " + cardRequirement);
    };
  }

  @Override
  public CombinationType getCombinationName() {
    return CombinationType.UNIFORMITY;
  }
}
