package com.dicedicebaby.dto;

public record RegistrationRequestDTO(
        String username,
        String email,
        String password
) {}