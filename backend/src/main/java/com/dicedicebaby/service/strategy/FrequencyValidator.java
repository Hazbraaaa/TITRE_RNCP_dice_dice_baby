package com.dicedicebaby.service.strategy;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FrequencyValidator implements CombinationValidator {

  /** {@inheritDoc} */
  @Override
  public boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement) {
    if (diceValues == null || diceValues.size() < Constant.GameData.DICE_COUNT) {
      return false;
    }

    // Create a map of frequencies of each values
    Map<Integer, Long> frequencies =
        diceValues.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // Check and return if right frequency of dice validate requirement
    return switch (cardRequirement) {
      case DOUBLE_PAIR -> {
        long numberOfPairs = frequencies.values().stream().filter(count -> count >= 2).count();
        yield numberOfPairs == 2;
      }
      case FOUR_OF_A_KIND -> frequencies.containsValue(4L) || frequencies.containsValue(5L);
      case FULL_HOUSE -> frequencies.containsValue(3L) && frequencies.containsValue(2L);
      default ->
          throw new IllegalArgumentException(
              "Requirement non supporté par ce validateur : " + cardRequirement);
    };
  }

  /** {@inheritDoc} */
  @Override
  public CombinationType getCombinationName() {
    return CombinationType.FREQUENCY;
  }
}
