package com.dicedicebaby.service.strategy;

import com.dicedicebaby.enums.CardRequirement;
import com.dicedicebaby.enums.CombinationType;
import java.util.List;

public interface CombinationValidator {
  boolean isValid(List<Integer> diceValues, CardRequirement cardRequirement);

  CombinationType getCombinationName();
}
