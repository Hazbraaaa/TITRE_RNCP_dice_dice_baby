package com.dicedicebaby.config;

import java.util.List;

public class Constant {
  public static final String COOKIE_NAME = "jwt_session";
  public static final String SEPARATOR = "¤";

  public static class GameData {
    public record CardData(String combination, String color, int ptLvl1, int ptLvl2) {}

    public static final List<CardData> DEFAULT_CARDS =
        List.of(
            new CardData("Paire 1", "green", 1, 1),
            new CardData("Paire 2", "green", 1, 1),
            new CardData("Paire 3", "green", 1, 1),
            new CardData("Paire 4", "green", 1, 1),
            new CardData("Paire 5", "green", 1, 1),
            new CardData("Paire 6", "green", 1, 1),
            new CardData("Double Paire", "green", 3, 2),
            new CardData("Brelan 1", "blue", 1, 2),
            new CardData("Brelan 2", "blue", 1, 2),
            new CardData("Brelan 3", "blue", 1, 2),
            new CardData("Brelan 4", "blue", 1, 2),
            new CardData("Brelan 5", "blue", 1, 2),
            new CardData("Brelan 6", "blue", 1, 2),
            new CardData("12/13/14", "red", 3, 2),
            new CardData("21/22/23", "red", 3, 2),
            new CardData("Inférieur à 9", "red", 4, 3),
            new CardData("Supérieur à 26", "red", 4, 3),
            new CardData("Tous Pairs", "red", 3, 2),
            new CardData("Tous Impairs", "red", 3, 2),
            new CardData("Petite Suite", "red", 3, 2),
            new CardData("Grande Suite", "red", 4, 3),
            new CardData("Full", "red", 4, 3),
            new CardData("Carré", "red", 3, 3),
            new CardData("Tous différents", "red", 3, 3),
            new CardData("Tous identiques", "red", 6, 4));
  }

  private Constant() {}
}
