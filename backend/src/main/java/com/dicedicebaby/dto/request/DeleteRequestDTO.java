package com.dicedicebaby.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DeleteRequestDTO(
    @NotBlank(message = "Le pseudo est obligatoire")
        @Size(min = 3, max = 20, message = "Le pseudo doit faire entre 3 et 20 caractères")
        String username,
    @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
        @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message =
                "Le mot de passe doit contenir au moins un chiffre, une minuscule, une majuscule et un caractère spécial (@#$%^&+=!)")
        String password,
    int playerNumber) {}
