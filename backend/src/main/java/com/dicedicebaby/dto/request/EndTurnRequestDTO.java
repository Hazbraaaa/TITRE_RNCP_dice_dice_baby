package com.dicedicebaby.dto.request;

import java.util.List;

public record EndTurnRequestDTO(Long diceSetId, List<Long> keptDiceIds, Long gameCardId) {}
