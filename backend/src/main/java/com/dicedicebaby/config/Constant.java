package com.dicedicebaby.config;

import com.dicedicebaby.enums.CardColor;
import com.dicedicebaby.enums.CardRequirement;
import java.util.List;

public class Constant {
  public static final String COOKIE_NAME = "jwt_session";
  public static final String SEPARATOR = "¤";

  public static class GameData {
    public record CardData(CardRequirement combination, CardColor color, int ptLvl1, int ptLvl2) {}

    public static final List<CardData> DEFAULT_CARDS =
        List.of(
            new CardData(CardRequirement.PAIR_1, CardColor.green, 1, 1),
            new CardData(CardRequirement.PAIR_2, CardColor.green, 1, 1),
            new CardData(CardRequirement.PAIR_3, CardColor.green, 1, 1),
            new CardData(CardRequirement.PAIR_4, CardColor.green, 1, 1),
            new CardData(CardRequirement.PAIR_5, CardColor.green, 1, 1),
            new CardData(CardRequirement.PAIR_6, CardColor.green, 1, 1),
            new CardData(CardRequirement.DOUBLE_PAIR, CardColor.green, 3, 2),
            new CardData(CardRequirement.THREE_OF_A_KIND_1, CardColor.blue, 2, 1),
            new CardData(CardRequirement.THREE_OF_A_KIND_2, CardColor.blue, 2, 1),
            new CardData(CardRequirement.THREE_OF_A_KIND_3, CardColor.blue, 2, 1),
            new CardData(CardRequirement.THREE_OF_A_KIND_4, CardColor.blue, 2, 1),
            new CardData(CardRequirement.THREE_OF_A_KIND_5, CardColor.blue, 2, 1),
            new CardData(CardRequirement.THREE_OF_A_KIND_6, CardColor.blue, 2, 1),
            new CardData(CardRequirement.SUM_12_13_14, CardColor.red, 3, 2),
            new CardData(CardRequirement.SUM_21_22_23, CardColor.red, 3, 2),
            new CardData(CardRequirement.LESS_THAN_9, CardColor.red, 4, 3),
            new CardData(CardRequirement.GREATER_THAN_26, CardColor.red, 4, 3),
            new CardData(CardRequirement.ALL_EVEN, CardColor.red, 3, 2),
            new CardData(CardRequirement.ALL_ODD, CardColor.red, 3, 2),
            new CardData(CardRequirement.SMALL_STRAIGHT, CardColor.red, 3, 2),
            new CardData(CardRequirement.LARGE_STRAIGHT, CardColor.red, 4, 3),
            new CardData(CardRequirement.FULL_HOUSE, CardColor.red, 4, 3),
            new CardData(CardRequirement.FOUR_OF_A_KIND, CardColor.red, 3, 3),
            new CardData(CardRequirement.ALL_DIFFERENT, CardColor.red, 3, 3),
            new CardData(CardRequirement.ALL_IDENTICAL, CardColor.red, 6, 4));
  }

  private Constant() {}
}
