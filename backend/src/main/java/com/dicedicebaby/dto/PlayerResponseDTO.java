package com.dicedicebaby.dto;

public record PlayerResponseDTO(
        Long playerId,
        String username,
        Boolean isGuest,
        int playerNumber,
        int score
) {}
