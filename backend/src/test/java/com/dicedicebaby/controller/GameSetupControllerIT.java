package com.dicedicebaby.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.request.GuestRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GameSetupControllerIT {

  // Main entry point for server-side Spring MVC testing
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void setup_WithValidCookie_ShouldCreateGameAndReturnCreated() throws Exception {
    // region GIVEN
    GuestRequestDTO guestRequest = new GuestRequestDTO("Pingu", 1);

    MvcResult guestResult =
        mockMvc
            .perform(
                post("/auth/guest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(guestRequest)))
            .andExpect(status().isCreated())
            .andExpect(cookie().exists(Constant.COOKIE_NAME))
            .andReturn();

    Cookie sessionCookie = guestResult.getResponse().getCookie(Constant.COOKIE_NAME);
    // endregion

    // region WHEN
    mockMvc
        .perform(post("/game/setup").cookie(sessionCookie))
        // endregion

        // region THEN
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());
    // endregion
  }
}
