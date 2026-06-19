package com.dicedicebaby.dto.request;

import java.util.List;

public record EndTurnRequestDTO(Long diceSetId, List<Long> keptDiceIds) {}
// Plus tard avec des infos de la tuile choisi
