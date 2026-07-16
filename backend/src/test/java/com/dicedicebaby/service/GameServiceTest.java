package com.dicedicebaby.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.request.RollRequestDTO;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.DiceEntity;
import com.dicedicebaby.entity.DiceSetEntity;
import com.dicedicebaby.entity.GameEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.enums.ApiErrorCode;
import com.dicedicebaby.enums.GameState;
import com.dicedicebaby.exception.ApiException;
import com.dicedicebaby.mapper.GameMapper;
import com.dicedicebaby.repository.GameCardRepository;
import com.dicedicebaby.repository.GameRepository;
import com.dicedicebaby.security.CookieUtils;
import com.dicedicebaby.security.JwtUtils;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

  @Mock private GameRepository gameRepository;

  @Mock private GameCardRepository gameCardRepository;

  @Mock private CardValidationService cardValidationService;

  @Mock private GameMapper gameMapper;

  @Mock private CookieUtils cookieUtils;

  @Mock private JwtUtils jwtUtils;

  @InjectMocks private GameService gameService;

  @Test
  void rollDices_WithAuthorizedSession_ShouldRollDice() {
    // region GIVEN
    String cookie = "valid-cookie";
    RollRequestDTO request = new RollRequestDTO(1L, List.of());

    GameEntity game = createGame();
    GameResponseDTO expectedResponse = mock(GameResponseDTO.class);

    when(jwtUtils.extractValidUsernames(cookie)).thenReturn(Set.of("pingu", "pong"));

    when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

    when(gameMapper.mapToGameResponseDTO(game)).thenReturn(expectedResponse);
    // endregion

    // region WHEN
    GameResponseDTO result = gameService.rollDices(request, cookie);
    // endregion

    // region THEN
    assertThat(result).isEqualTo(expectedResponse);
    assertThat(game.getRollsLeft()).isEqualTo(Constant.GameData.MAX_ROLLS_LEFT - 1);

    verify(jwtUtils).extractValidUsernames(cookie);
    verify(gameRepository).findById(1L);
    verify(gameMapper).mapToGameResponseDTO(game);
    // endregion
  }

  @Test
  void rollDices_WithIncompleteSession_ShouldThrowGameAccessDenied() {
    // region GIVEN
    String cookie = "incomplete-cookie";
    RollRequestDTO request = new RollRequestDTO(1L, List.of());

    GameEntity game = createGame();

    when(jwtUtils.extractValidUsernames(cookie)).thenReturn(Set.of("pingu"));

    when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
    // endregion

    // region WHEN / THEN
    assertThatThrownBy(() -> gameService.rollDices(request, cookie))
        .isInstanceOf(ApiException.class)
        .satisfies(
            exception ->
                assertThat(((ApiException) exception).getErrorCode())
                    .isEqualTo(ApiErrorCode.GAME_ACCESS_DENIED));

    assertThat(game.getRollsLeft()).isEqualTo(Constant.GameData.MAX_ROLLS_LEFT);

    verifyNoInteractions(gameMapper);
    // endregion
  }

  @Test
  void rollDices_WithFinishedGame_ShouldThrowGameAlreadyFinished() {
    // region GIVEN
    String cookie = "valid-cookie";
    RollRequestDTO request = new RollRequestDTO(1L, List.of());

    GameEntity game = createGame();
    game.setState(GameState.FINISHED);

    when(jwtUtils.extractValidUsernames(cookie)).thenReturn(Set.of("pingu", "pong"));

    when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
    // endregion

    // region WHEN / THEN
    assertThatThrownBy(() -> gameService.rollDices(request, cookie))
        .isInstanceOf(ApiException.class)
        .satisfies(
            exception ->
                assertThat(((ApiException) exception).getErrorCode())
                    .isEqualTo(ApiErrorCode.GAME_ALREADY_FINISHED));

    assertThat(game.getRollsLeft()).isEqualTo(Constant.GameData.MAX_ROLLS_LEFT);

    verifyNoInteractions(gameMapper);
    // endregion
  }

  @Test
  void rollDices_WithNoRollLeft_ShouldThrowNoRollLeft() {
    // region GIVEN
    String cookie = "valid-cookie";
    RollRequestDTO request = new RollRequestDTO(1L, List.of());

    GameEntity game = createGame();
    game.setRollsLeft(Constant.GameData.NO_ROLLS_LEFT);

    when(jwtUtils.extractValidUsernames(cookie)).thenReturn(Set.of("pingu", "pong"));

    when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
    // endregion

    // region WHEN / THEN
    assertThatThrownBy(() -> gameService.rollDices(request, cookie))
        .isInstanceOf(ApiException.class)
        .satisfies(
            exception ->
                assertThat(((ApiException) exception).getErrorCode())
                    .isEqualTo(ApiErrorCode.NO_ROLL_LEFT));

    verifyNoInteractions(gameMapper);
    // endregion
  }

  private GameEntity createGame() {
    PlayerEntity player1 = new PlayerEntity();
    player1.setId(1L);
    player1.setPlayerUsername("Pingu");
    player1.setPlayerNumber(1);

    PlayerEntity player2 = new PlayerEntity();
    player2.setId(2L);
    player2.setPlayerUsername("Pong");
    player2.setPlayerNumber(2);

    DiceEntity dice1 = new DiceEntity();
    dice1.setId(1L);
    dice1.setValue(1);

    DiceEntity dice2 = new DiceEntity();
    dice2.setId(2L);
    dice2.setValue(2);

    DiceSetEntity diceSet = new DiceSetEntity();
    diceSet.setDices(List.of(dice1, dice2));

    dice1.setDiceSet(diceSet);
    dice2.setDiceSet(diceSet);

    GameEntity game = new GameEntity();
    game.setId(1L);
    game.setState(GameState.IN_PROGRESS);
    game.setPlayers(List.of(player1, player2));
    game.setCurrentPlayer(player1);
    game.setDiceSet(diceSet);
    game.setRollsLeft(Constant.GameData.MAX_ROLLS_LEFT);

    player1.setGame(game);
    player2.setGame(game);
    diceSet.setGame(game);

    return game;
  }
}
