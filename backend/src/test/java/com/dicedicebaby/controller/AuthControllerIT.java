package com.dicedicebaby.controller;

import com.dicedicebaby.dto.GuestRequestDTO;
import com.dicedicebaby.dto.LoginRequestDTO;
import com.dicedicebaby.dto.RegistrationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerIT {

    // Main entry point for server-side Spring MVC testing
    @Autowired
    private MockMvc mockMvc;

    // Used to serialize Java objects into JSON strings
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldCreateAccountAndPlayerInDatabase() throws Exception {
        //region GIVEN
        // Prepare input data
        RegistrationRequestDTO request = new RegistrationRequestDTO("Pingu", "pingu@test.com", "pass", 1);
        //endregion

        //region WHEN
        // Execute request and verify results
        mockMvc.perform(post("/api/auth/register")
                        // Mandatory to avoid 403 when security is enabled
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        // Convert DTO to JSON
                        .content(objectMapper.writeValueAsString(request)))
                //endregion
                //region THEN
                // Validate HTTP status 201 Created
                .andExpect(status().isCreated())
                // Validate specific fields in the JSON response
                .andExpect(jsonPath("$.username").value("Pingu"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
                //endregion

    }

    @Test
    void login_ShouldReturnPlayerFromDatabase() throws Exception {
        //region GIVEN
        // Register before trying to login
        RegistrationRequestDTO signup = new RegistrationRequestDTO("Pingu", "pingu@test.com", "pass", 1);
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signup)));

        // Prepare input data
        LoginRequestDTO request = new LoginRequestDTO("pingu@test.com", "pass", 1);
        //endregion

        //region WHEN
        // Execute request and verify results
        mockMvc.perform(post("/api/auth/login")
                        // Mandatory to avoid 403 when security is enabled
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        // Convert DTO to JSON
                        .content(objectMapper.writeValueAsString(request)))
                //endregion
                //region THEN
                // Validate HTTP status 202 Accepted
                .andExpect(status().isAccepted())
                // Validate specific fields in the JSON response
                .andExpect(jsonPath("$.username").value("Pingu"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
                //endregion
    }

    @Test
    void guest_ShouldCreatePlayerInDatabase() throws Exception {
        //region GIVEN
        // Prepare input data
        GuestRequestDTO request = new GuestRequestDTO("Pingu", 1);
        //endregion

        //region WHEN
        // Execute request and verify results
        mockMvc.perform(post("/api/auth/guest")
                        // Mandatory to avoid 403 when security is enabled
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        // Convert DTO to JSON
                        .content(objectMapper.writeValueAsString(request)))
                //endregion
                //region THEN
                // Validate HTTP status 201 Created
                .andExpect(status().isCreated())
                // Validate specific fields in the JSON response
                .andExpect(jsonPath("$.username").value("Pingu"))
                .andExpect(jsonPath("$.token").value(nullValue()));
                //endregion
    }
}