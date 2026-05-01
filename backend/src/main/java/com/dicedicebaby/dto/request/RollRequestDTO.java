package com.dicedicebaby.dto.request;

import java.util.List;

public record RollRequestDTO(Long diceSetId, List<Long> keptDiceIds) {}
