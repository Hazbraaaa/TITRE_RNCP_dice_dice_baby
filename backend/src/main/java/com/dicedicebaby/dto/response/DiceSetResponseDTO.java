package com.dicedicebaby.dto.response;

import java.util.List;

public record DiceSetResponseDTO(Long id, List<DiceResponseDTO> dices) {}
