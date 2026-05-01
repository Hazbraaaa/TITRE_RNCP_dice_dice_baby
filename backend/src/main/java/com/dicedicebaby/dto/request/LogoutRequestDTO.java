package com.dicedicebaby.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogoutRequestDTO(
    @NotBlank(message = "Le pseudo est obligatoire")
        @Size(min = 3, max = 20, message = "Le pseudo doit faire entre 3 et 20 caractères")
        String username,
    int playerNumber) {}
