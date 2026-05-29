package com.dicedicebaby.dto.response;

public record ConnectedPlayerResponseDTO(
    Long playerId, String username, Boolean isGuest, int playerNumber) {}
