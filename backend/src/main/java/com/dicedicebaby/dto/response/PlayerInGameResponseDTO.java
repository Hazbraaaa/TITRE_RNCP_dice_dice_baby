package com.dicedicebaby.dto.response;

public record PlayerInGameResponseDTO(
    Long playerId, int playerNumber, String username, int score, int remainingChips) {}
