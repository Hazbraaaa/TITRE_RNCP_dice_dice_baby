package com.dicedicebaby.dto.response;

import com.dicedicebaby.enums.GameState;
import java.util.List;

public record GameResponseDTO(
    Long id,
    GameState state,
    int roundNumber,
    List<CardResponseDTO> board,
    DiceSetResponseDTO diceSet,
    PlayerInGameResponseDTO currentPlayer,
    List<PlayerInGameResponseDTO> players) {}
