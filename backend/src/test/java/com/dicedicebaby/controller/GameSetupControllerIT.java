package com.dicedicebaby.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dicedicebaby.config.Constant;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GameSetupControllerIT {

  // Main entry point for server-side Spring MVC testing
  @Autowired private MockMvc mockMvc;

  @Test
  void setup_WithValidCookie_ShouldCreateGameAndReturnCreated() throws Exception {
    // region GIVEN
    Cookie sessionCookie = new Cookie(Constant.COOKIE_NAME, "mocked-valid-jwt-token");
    // endregion
    // region WHEN
    mockMvc
        .perform(get("/game/setup"))
        // endregion
        // region Then
        .andExpect(status().isCreated());
    // endregion
  }
}
