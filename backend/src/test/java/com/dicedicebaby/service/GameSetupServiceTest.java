package com.dicedicebaby.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.response.GameResponseDTO;
import com.dicedicebaby.entity.*;
import com.dicedicebaby.mapper.GameMapper;
import com.dicedicebaby.repository.CardRepository;
import com.dicedicebaby.repository.GameRepository;
import com.dicedicebaby.repository.PlayerRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameSetupServiceTest {

  @Mock private CardRepository cardRepository;
  @Mock private PlayerRepository playerRepository;
  @Mock private GameRepository gameRepository;
  @Mock private GameMapper gameMapper;

  // Inject mocks above automatically
  @InjectMocks private GameSetupService gameSetupService;

  @Test
  void setupNewGame_ShouldInitializeCompleteGameAndReturnDTO() {
    // region GIVEN
    // Prepare input data
    String existingCookie = "tokenPlayer1" + Constant.SEPARATOR + "tokenPlayer2";

    // Simulate initial game creation (saveAndFlush)
    GameEntity savedInitialGame = new GameEntity();
    savedInitialGame.setId(100L);
    when(gameRepository.saveAndFlush(any(GameEntity.class))).thenReturn(savedInitialGame);

    // Prepare 2 players from token
    PlayerEntity player1 = new PlayerEntity();
    player1.setId(1L);
    player1.setPlayerUsername("Pingu");

    PlayerEntity player2 = new PlayerEntity();
    player2.setId(2L);
    player2.setPlayerUsername("Pong");

    when(playerRepository.findByCurrentToken("tokenPlayer1")).thenReturn(player1);
    when(playerRepository.findByCurrentToken("tokenPlayer2")).thenReturn(player2);

    // Prepare board of 16 cards
    List<CardEntity> board = new ArrayList<>();
    for (long i = 1; i <= 16; i++) {
      CardEntity card = new CardEntity();
      card.setId(i);
      board.add(card);
    }
    when(cardRepository.findRandomCards()).thenReturn(board);

    // Simulate DTO mapping
    GameResponseDTO expectedResponse = mock(GameResponseDTO.class);
    when(gameMapper.mapToGameResponseDTO(any(GameEntity.class))).thenReturn(expectedResponse);
    // endregion

    // region WHEN
    // Execute the service method
    GameResponseDTO result = gameSetupService.setupNewGame(existingCookie);
    // endregion

    // region THEN
    assertThat(result).isEqualTo(expectedResponse);

    // Verification of structure of the game saved
    verify(gameRepository)
        .save(
            argThat(
                game -> {
                  // Verify players
                  assertThat(game.getCurrentPlayer()).isEqualTo(player1);
                  assertThat(game.getPlayers()).hasSize(2);

                  // Verify board
                  assertThat(game.getBoard()).isNotNull();
                  assertThat(game.getBoard().getGameCards()).hasSize(16);
                  assertThat(game.getBoard().getGameCards().get(0).getCard().getId()).isEqualTo(1);

                  // Verify dice set
                  assertThat(game.getDiceSet()).isNotNull();
                  assertThat(game.getDiceSet().getDices()).hasSize(5);
                  assertThat(
                          game.getDiceSet().getDices().stream()
                              .allMatch(d -> d.getValue() == 1 && !d.isKept()))
                      .isTrue();

                  return true;
                }));
    // endregion
  }
}
