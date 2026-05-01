package com.dicedicebaby.dto.response;

import java.util.List;

public record RollResponseDTO(Long diceSetId, List<DiceResponseDTO> dices) {}
