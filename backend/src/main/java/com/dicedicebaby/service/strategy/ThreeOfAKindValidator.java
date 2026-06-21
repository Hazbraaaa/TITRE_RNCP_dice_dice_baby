package com.dicedicebaby.service.strategy;

import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ThreeOfAKindValidator implements CombinationValidator {

  @Override
  public boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement) {
    if (diceValues == null || diceValues.size() < 5) {
      return false;
    }

    // Determine target digit with cardRequirement
    int targetDigit =
        switch (cardRequirement) {
          case THREE_OF_A_KIND_1 -> 1;
          case THREE_OF_A_KIND_2 -> 2;
          case THREE_OF_A_KIND_3 -> 3;
          case THREE_OF_A_KIND_4 -> 4;
          case THREE_OF_A_KIND_5 -> 5;
          case THREE_OF_A_KIND_6 -> 6;
          default ->
              throw new IllegalArgumentException(
                  "Requirement non supporté par ce validateur : " + cardRequirement);
        };

    // Count and return if more than 3 digit identical
    long count = diceValues.stream().filter(v -> v == targetDigit).count();
    return count >= 3;
  }

  @Override
  public CombinationType getCombinationName() {
    return CombinationType.THREE_OF_A_KIND;
  }
}
