package com.dicedicebaby.service.strategy;

import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PairValidator implements CombinationValidator {

  @Override
  public boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement) {
    if (diceValues == null || diceValues.size() < 5) {
      return false;
    }

    // Determine target digit with cardRequirement
    int targetDigit =
        switch (cardRequirement) {
          case PAIR_1 -> 1;
          case PAIR_2 -> 2;
          case PAIR_3 -> 3;
          case PAIR_4 -> 4;
          case PAIR_5 -> 5;
          case PAIR_6 -> 6;
          default ->
              throw new IllegalArgumentException(
                  "Requirement non supporté par ce validateur : " + cardRequirement);
        };

    // Count and return if more than 2 digit identical
    long count = diceValues.stream().filter(v -> v == targetDigit).count();
    return count >= 2;
  }

  @Override
  public CombinationType getCombinationName() {
    return CombinationType.PAIR;
  }
}
