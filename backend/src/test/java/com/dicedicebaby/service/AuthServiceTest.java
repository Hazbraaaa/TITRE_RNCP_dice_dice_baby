package com.dicedicebaby.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.request.GuestRequestDTO;
import com.dicedicebaby.dto.request.LoginRequestDTO;
import com.dicedicebaby.dto.request.RegistrationRequestDTO;
import com.dicedicebaby.dto.response.ConnectedPlayerResponseDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.security.CookieUtils;
import com.dicedicebaby.security.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Initialize Mockito for JUnit 5
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @Mock private AccountService accountService;

  @Mock private PlayerService playerService;

  @Mock private JwtUtils jwtUtils;

  @Mock private CookieUtils cookieUtils;

  @Mock private HttpServletResponse response;

  // Inject mocks above automatically
  @InjectMocks private AuthService authService;

  @Test
  void getCurrentSession_ShouldWorkCorrectly() {
    // region GIVEN
    // Prepare input data
    String existingCookie = "token1" + Constant.SEPARATOR + "token2";

    // Use real entity to have input data
    PlayerEntity player1 = new PlayerEntity();
    player1.setId(1L);
    player1.setPlayerUsername("Pingu");
    player1.setIsGuest(false);
    player1.setPlayerNumber(1);

    PlayerEntity player2 = new PlayerEntity();
    player2.setId(2L);
    player2.setPlayerUsername("Pong");
    player2.setIsGuest(true);
    player2.setPlayerNumber(2);

    // Define mock behavior for dependencies
    when(jwtUtils.extractUsername("token1")).thenReturn("Pingu");
    when(jwtUtils.extractUsername("token2")).thenReturn("Pong");
    when(playerService.getPlayerByPlayerUsername("Pingu")).thenReturn(player1);
    when(playerService.getPlayerByPlayerUsername("Pong")).thenReturn(player2);
    // endregion

    // region WHEN
    // Execute the service method
    List<ConnectedPlayerResponseDTO> result = authService.getCurrentSession(existingCookie);
    // endregion

    // region THEN
    // Assert the returned DTO values and verify that the token was actually set on the player
    // entity
    assertThat(result).hasSize(2);
    assertThat(result.get(0).username()).isEqualTo("Pingu");
    assertThat(result.get(1).username()).isEqualTo("Pong");
    assertThat(result.get(1).isGuest()).isTrue();
    // endregion
  }

  @Test
  void register_ShouldWorkCorrectly() {
    // region GIVEN
    // Prepare input data
    RegistrationRequestDTO request =
        new RegistrationRequestDTO("Pingu", "pingu@test.com", "pass", 1);
    String existingCookie = "token-in-cookie";

    // Use real entity to have input data
    AccountEntity account = new AccountEntity();
    account.setId(10L);
    account.setUsername("Pingu");

    // Use a mock for the entity to verify internal method calls (setters)
    PlayerEntity player = mock(PlayerEntity.class);
    when(player.getId()).thenReturn(1L);
    when(player.getPlayerUsername()).thenReturn("Pingu");
    when(player.getIsGuest()).thenReturn(false);
    when(player.getPlayerNumber()).thenReturn(1);

    // Define mock behavior for dependencies
    when(accountService.registerNewAccount(anyString(), anyString(), anyString()))
        .thenReturn(account);
    when(playerService.createPlayerForAccount(any(), anyInt())).thenReturn(player);
    when(jwtUtils.generateToken("Pingu")).thenReturn("mock-jwt-token");
    // endregion

    // region WHEN
    // Execute the service method
    ConnectedPlayerResponseDTO result = authService.register(request, response, existingCookie);
    // endregion

    // region THEN
    // Assert the returned DTO values and verify that the token was actually set on the player
    // entity
    assertThat(result.username()).isEqualTo("Pingu");
    assertThat(result.isGuest()).isFalse();
    verify(player).setCurrentToken("mock-jwt-token");
    verify(cookieUtils).addTokenToCookie(eq("mock-jwt-token"), eq(existingCookie), eq(response));
    // endregion
  }

  @Test
  void login_ShouldWorkCorrectly() {
    // region GIVEN
    // Prepare input data
    LoginRequestDTO request = new LoginRequestDTO("pingu@test.com", "pass", 2);
    String existingCookie = "token-in-cookie";

    // Use real entity to have input data
    AccountEntity account = new AccountEntity();
    account.setId(10L);
    account.setUsername("Pingu");

    // Use a mock for the entity to verify internal method calls (setters)
    PlayerEntity player = mock(PlayerEntity.class);
    when(player.getId()).thenReturn(1L);
    when(player.getPlayerUsername()).thenReturn("Pingu");
    when(player.getIsGuest()).thenReturn(false);
    when(player.getPlayerNumber()).thenReturn(2);

    // Define mock behavior for dependencies
    when(accountService.getAccount(anyString(), anyString())).thenReturn(account);
    when(playerService.getPlayerByAccount(account)).thenReturn(player);
    when(jwtUtils.generateToken("Pingu")).thenReturn("new-token");
    // endregion

    // region WHEN
    // Execute the service method
    ConnectedPlayerResponseDTO result = authService.login(request, response, existingCookie);
    // endregion

    // region THEN
    // Assert the returned DTO values and verify that the token was actually set on the player
    // entity
    assertThat(result.username()).isEqualTo("Pingu");
    verify(player).setPlayerNumber(2);
    verify(player).setCurrentToken("new-token");
    verify(cookieUtils).addTokenToCookie(eq("new-token"), eq(existingCookie), eq(response));
    // endregion
  }

  @Test
  void guest_ShouldWorkCorrectly() {
    // region GIVEN
    // Prepare input data
    GuestRequestDTO request = new GuestRequestDTO("Pingu", 1);
    String existingCookie = "token-in-cookie";

    // Use a mock for the entity to verify internal method calls (setters)
    PlayerEntity player = mock(PlayerEntity.class);
    when(player.getId()).thenReturn(5L);
    when(player.getPlayerUsername()).thenReturn("Pingu");
    when(player.getIsGuest()).thenReturn(true);
    when(player.getPlayerNumber()).thenReturn(1);

    // Define mock behavior for dependencies
    when(playerService.createPlayerForGuest("Pingu", 1)).thenReturn(player);
    when(jwtUtils.generateToken("Pingu")).thenReturn("guest-jwt-token");
    // endregion

    // region WHEN
    ConnectedPlayerResponseDTO result = authService.guest(request, response, existingCookie);
    // endregion

    // region THEN
    // Assert the returned DTO values and verify that the token is set to null on the player entity
    // and no interactions with accountService or jwtUtils
    assertThat(result.username()).isEqualTo("Pingu");
    assertThat(result.isGuest()).isTrue();
    verify(player).setCurrentToken("guest-jwt-token");
    verify(cookieUtils).addTokenToCookie(eq("guest-jwt-token"), eq(existingCookie), eq(response));
    verifyNoInteractions(accountService);
    // endregion
  }
}
