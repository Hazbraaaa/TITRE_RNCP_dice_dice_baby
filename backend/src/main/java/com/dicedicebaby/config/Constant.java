package com.dicedicebaby.config;

import com.dicedicebaby.enums.CardColor;
import java.util.List;

public class Constant {
  public static final String COOKIE_NAME = "jwt_session";
  public static final String SEPARATOR = "¤";

  public static class GameData {
    public record CardData(String combination, CardColor color, int ptLvl1, int ptLvl2) {}

    public static final List<CardData> DEFAULT_CARDS =
        List.of(
            new CardData("Paire 1", CardColor.green, 1, 1),
            new CardData("Paire 2", CardColor.green, 1, 1),
            new CardData("Paire 3", CardColor.green, 1, 1),
            new CardData("Paire 4", CardColor.green, 1, 1),
            new CardData("Paire 5", CardColor.green, 1, 1),
            new CardData("Paire 6", CardColor.green, 1, 1),
            new CardData("Double Paire", CardColor.green, 3, 2),
            new CardData("Brelan 1", CardColor.blue, 2, 1),
            new CardData("Brelan 2", CardColor.blue, 2, 1),
            new CardData("Brelan 3", CardColor.blue, 2, 1),
            new CardData("Brelan 4", CardColor.blue, 2, 1),
            new CardData("Brelan 5", CardColor.blue, 2, 1),
            new CardData("Brelan 6", CardColor.blue, 2, 1),
            new CardData("12/13/14", CardColor.red, 3, 2),
            new CardData("21/22/23", CardColor.red, 3, 2),
            new CardData("Inférieur à 9", CardColor.red, 4, 3),
            new CardData("Supérieur à 26", CardColor.red, 4, 3),
            new CardData("Tous Pairs", CardColor.red, 3, 2),
            new CardData("Tous Impairs", CardColor.red, 3, 2),
            new CardData("Petite Suite", CardColor.red, 3, 2),
            new CardData("Grande Suite", CardColor.red, 4, 3),
            new CardData("Full", CardColor.red, 4, 3),
            new CardData("Carré", CardColor.red, 3, 3),
            new CardData("Tous différents", CardColor.red, 3, 3),
            new CardData("Tous identiques", CardColor.red, 6, 4));
  }

  private Constant() {}
}
