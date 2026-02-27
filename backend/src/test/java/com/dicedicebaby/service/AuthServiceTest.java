package com.dicedicebaby.service;

import com.dicedicebaby.dto.GuestRequestDTO;
import com.dicedicebaby.dto.LoginRequestDTO;
import com.dicedicebaby.dto.PlayerResponseDTO;
import com.dicedicebaby.dto.RegistrationRequestDTO;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.security.CookieUtils;
import com.dicedicebaby.security.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// Initialize Mockito for JUnit 5
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private PlayerService playerService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private CookieUtils cookieUtils;

    @Mock
    private HttpServletResponse response;

    // Inject mocks above automatically
    @InjectMocks
    private AuthService authService;

    @Test
    void register_ShouldWorkCorrectly() {
        //region GIVEN
        // Prepare input data
        RegistrationRequestDTO request = new RegistrationRequestDTO("Pingu", "pingu@test.com", "pass", 1);
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
        when(player.getScore()).thenReturn(0);

        // Define mock behavior for dependencies
        when(accountService.registerNewAccount(anyString(), anyString(), anyString())).thenReturn(account);
        when(playerService.createPlayerForAccount(any(), anyInt())).thenReturn(player);
        when(jwtUtils.generateToken("Pingu")).thenReturn("mock-jwt-token");
        //endregion

        //region WHEN
        // Execute the service method
        PlayerResponseDTO result = authService.register(request, response, existingCookie);
        //endregion

        //region THEN
        // Assert the returned DTO values and verify that the token was actually set on the player entity
        assertThat(result.username()).isEqualTo("Pingu");
        assertThat(result.isGuest()).isFalse();
        verify(player).setCurrentToken("mock-jwt-token");
        verify(cookieUtils).addTokenToCookie(eq("mock-jwt-token"), eq(existingCookie), eq(response));
        //endregion
    }

    @Test
    void login_ShouldWorkCorrectly() {
        //region GIVEN
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

        // Define mock behavior for dependencies
        when(accountService.loginAccount(anyString(), anyString())).thenReturn(account);
        when(playerService.getPlayerByAccount(account)).thenReturn(player);
        when(jwtUtils.generateToken("Pingu")).thenReturn("new-token");
        //endregion

        //region WHEN
        // Execute the service method
        PlayerResponseDTO result = authService.login(request, response, existingCookie);
        //endregion

        //region THEN
        // Assert the returned DTO values and verify that the token was actually set on the player entity
        assertThat(result.username()).isEqualTo("Pingu");
        verify(player).setPlayerNumber(2);
        verify(player).setCurrentToken("new-token");
        verify(cookieUtils).addTokenToCookie(eq("mock-jwt-token"), eq(existingCookie), eq(response));
        //endregion
    }

    @Test
    void guest_ShouldWorkCorrectly() {
        //region GIVEN
        // Prepare input data
        GuestRequestDTO request = new GuestRequestDTO("Pingu", 1);

        // Use real entity to have input data
        PlayerEntity player = new PlayerEntity();
        player.setId(5L);
        player.setPlayerUsername("GuestUser");
        player.setIsGuest(true);
        player.setPlayerNumber(1);
        player.setScore(0);

        // Define mock behavior for dependencies
        when(playerService.createPlayerForGuest("Pingu", 1)).thenReturn(player);
        //endregion

        //region WHEN
        PlayerResponseDTO result = authService.guest(request);
        //endregion

        //region THEN
        // Assert the returned DTO values and verify that the token is set to null on the player entity and no interactions with accountService or jwtUtils
        assertThat(result.username()).isEqualTo("GuestUser");
        assertThat(result.isGuest()).isTrue();
        verifyNoInteractions(accountService);
        verifyNoInteractions(jwtUtils);
        verifyNoInteractions(cookieUtils);
        //endregion
    }
}