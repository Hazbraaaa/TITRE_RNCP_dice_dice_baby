package com.dicedicebaby.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateRequestDTO(
    @Size(min = 3, max = 20, message = "Le pseudo doit faire entre 3 et 20 caractères")
        String username,
    @Email(message = "Format d'email invalide") String email,
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
        @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message =
                "Le mot de passe doit contenir au moins un chiffre, une minuscule, une majuscule et un caractère spécial (@#$%^&+=!)")
        String newPassword,
    @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
        @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message =
                "Le mot de passe doit contenir au moins un chiffre, une minuscule, une majuscule et un caractère spécial (@#$%^&+=!)")
        String currentPassword,
    int playerNumber) {}
