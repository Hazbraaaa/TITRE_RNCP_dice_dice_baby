package com.dicedicebaby.dto.response;

import com.dicedicebaby.enums.CardColor;

public record CardResponseDTO(
    Long id,
    String combination,
    CardColor color,
    int pointLvl1,
    int pointLvl2,
    Integer ownerPointLvl1,
    Integer ownerPointLvl2) {}
