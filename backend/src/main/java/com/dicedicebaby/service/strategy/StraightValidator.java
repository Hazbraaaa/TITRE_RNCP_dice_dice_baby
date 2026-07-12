package com.dicedicebaby.service.strategy;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StraightValidator implements CombinationValidator {

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

    // Check and return if straight validate requirement
    return switch (cardRequirement) {
      case LARGE_STRAIGHT -> {
        // Sort dices by values
        List<Integer> sortedDices = diceValues.stream().sorted().toList();
        // Verify if one of the two possibilities
        yield sortedDices.equals(List.of(1, 2, 3, 4, 5))
            || sortedDices.equals(List.of(2, 3, 4, 5, 6));
      }
      case SMALL_STRAIGHT -> {
        // Dice values put in a set that sort and delete double(TreeSet)
        Set<Integer> uniqueSortedDices = new TreeSet<>(diceValues);
        // Transform Set in a String of number
        String diceSequence =
            uniqueSortedDices.stream().map(String::valueOf).collect(Collectors.joining());
        // Verify if one of the three possibilities
        yield diceSequence.contains("1234")
            || diceSequence.contains("2345")
            || diceSequence.contains("3456");
      }
      default ->
          throw new IllegalArgumentException(
              "Requirement non supporté par ce validateur : " + cardRequirement);
    };
  }

  /** {@inheritDoc} */
  @Override
  public CombinationType getCombinationName() {
    return CombinationType.STRAIGHT;
  }
}
