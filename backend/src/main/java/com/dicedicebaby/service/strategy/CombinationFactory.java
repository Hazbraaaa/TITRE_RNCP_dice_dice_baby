package com.dicedicebaby.service.strategy;

import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CombinationFactory {

  private final Map<CombinationType, CombinationValidator> validatorMap = new HashMap<>();

  // Create the validator map with all combination validator
  @Autowired
  public CombinationFactory(List<CombinationValidator> validators) {
    for (CombinationValidator validator : validators) {
      validatorMap.put(validator.getCombinationName(), validator);
    }
  }

  // Guide to the right validator using requirement name
  public CombinationValidator getValidator(CardRequirement requirement) {
    String requirementName = requirement.name();

    // Pair
    if (requirementName.startsWith("PAIR_")) {
      return validatorMap.get(CombinationType.PAIR);
    }

    // Three of a kind
    if (requirementName.startsWith("THREE_OF_A_KIND_")) {
      return validatorMap.get(CombinationType.THREE_OF_A_KIND);
    }

    // Straight
    if (requirementName.equals("SMALL_STRAIGHT") || requirementName.equals("LARGE_STRAIGHT")) {
      return validatorMap.get(CombinationType.STRAIGHT);
    }

    // Frequency
    if (requirementName.equals("DOUBLE_PAIR")
        || requirementName.equals("FOUR_OF_A_KIND")
        || requirementName.equals("FULL_HOUSE")) {
      return validatorMap.get(CombinationType.FREQUENCY);
    }

    // Uniformity
    if (requirementName.equals("ALL_IDENTICAL") || requirementName.equals("ALL_DIFFERENT")) {
      return validatorMap.get(CombinationType.UNIFORMITY);
    }

    // Sum
    if (requirementName.startsWith("SUM_")
        || requirementName.equals("LESS_THAN_9")
        || requirementName.equals("GREATER_THAN_26")) {
      return validatorMap.get(CombinationType.SUM);
    }

    // Parity
    if (requirementName.equals("ALL_EVEN") || requirementName.equals("ALL_ODD")) {
      return validatorMap.get(CombinationType.PARITY);
    }

    throw new IllegalArgumentException(
        "Aucun validateur enregistré pour la combinaison : " + requirement);
  }
}
