package com.dicedicebaby.dto.response;

import com.dicedicebaby.enums.CardColor;
import com.dicedicebaby.enums.CardRequirement;

public record CardResponseDTO(
    Long id,
    CardRequirement combination,
    CardColor color,
    int pointLvl1,
    int pointLvl2,
    Integer ownerPointLvl1,
    Integer ownerPointLvl2) {}
