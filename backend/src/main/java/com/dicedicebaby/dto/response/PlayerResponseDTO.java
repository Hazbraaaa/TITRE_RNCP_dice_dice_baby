package com.dicedicebaby.dto.response;

public record PlayerResponseDTO(
    Long playerId, String username, Boolean isGuest, int playerNumber, int score) {}
