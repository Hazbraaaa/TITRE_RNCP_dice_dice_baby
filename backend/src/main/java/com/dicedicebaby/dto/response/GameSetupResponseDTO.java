package com.dicedicebaby.dto.response;

import java.util.List;

public record GameSetupResponseDTO(
    List<CardResponseDTO> board, Long diceSetId, List<DiceResponseDTO> dices) {}
