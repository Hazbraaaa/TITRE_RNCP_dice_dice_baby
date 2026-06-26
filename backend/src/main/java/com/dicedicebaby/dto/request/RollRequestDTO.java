package com.dicedicebaby.dto.request;

import java.util.List;

public record RollRequestDTO(Long gameId, List<Long> keptDiceIds) {}
