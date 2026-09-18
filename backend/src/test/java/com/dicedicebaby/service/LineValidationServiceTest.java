package com.dicedicebaby.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.entity.BoardEntity;
import com.dicedicebaby.entity.GameCardEntity;
import com.dicedicebaby.entity.PlayerEntity;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LineValidationServiceTest {

  private LineValidationService lineValidationService;
  private PlayerEntity player;
  private PlayerEntity opponent;
  private BoardEntity board;

  @BeforeEach
  void setUp() {
    lineValidationService = new LineValidationService();

    player = new PlayerEntity();
    player.setId(1L);

    opponent = new PlayerEntity();
    opponent.setId(2L);

    List<GameCardEntity> cards = new ArrayList<>();
    for (long id = 1; id <= Constant.GameData.BOARD_CARD_COUNT; id++) {
      GameCardEntity card = new GameCardEntity();
      card.setId(id);
      cards.add(card);
    }

    board = new BoardEntity();
    board.setGameCards(cards);
  }

  @Test
  void validateLine_WhenPlayerCompletesRow_ShouldReturnTrue() {
    ownCardsAtLevelOne(4, 5, 6, 7);

    boolean result = lineValidationService.validateLine(board, cardAt(6), player);

    assertThat(result).isTrue();
  }

  @Test
  void validateLine_WhenPlayerCompletesColumnAcrossBothSlots_ShouldReturnTrue() {
    ownCardsAtLevelOne(2, 10);
    ownCardsAtLevelTwo(6, 14);

    boolean result = lineValidationService.validateLine(board, cardAt(14), player);

    assertThat(result).isTrue();
  }

  @Test
  void validateLine_WhenPlayerCompletesMainDiagonal_ShouldReturnTrue() {
    ownCardsAtLevelOne(0, 5, 10, 15);

    boolean result = lineValidationService.validateLine(board, cardAt(15), player);

    assertThat(result).isTrue();
  }

  @Test
  void validateLine_WhenPlayerCompletesAntiDiagonal_ShouldReturnTrue() {
    ownCardsAtLevelOne(3, 6, 9, 12);

    boolean result = lineValidationService.validateLine(board, cardAt(9), player);

    assertThat(result).isTrue();
  }

  @Test
  void validateLine_WhenLineContainsAnOpponentChipOnly_ShouldReturnFalse() {
    ownCardsAtLevelOne(8, 9, 10);
    cardAt(11).setOwnerPointLvl1(opponent);

    boolean result = lineValidationService.validateLine(board, cardAt(10), player);

    assertThat(result).isFalse();
  }

  @Test
  void validateLine_WhenAnotherRowIsComplete_ShouldReturnFalse() {
    ownCardsAtLevelOne(0, 1, 2, 3, 8);

    boolean result = lineValidationService.validateLine(board, cardAt(8), player);

    assertThat(result).isFalse();
  }

  @Test
  void validateLine_WhenValidatedCardIsNotOnBoard_ShouldThrowException() {
    GameCardEntity unknownCard = new GameCardEntity();
    unknownCard.setId(99L);

    assertThatThrownBy(() -> lineValidationService.validateLine(board, unknownCard, player))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("La carte validée n'appartient pas au plateau.");
  }

  @Test
  void validateLine_WhenBoardDoesNotContainSixteenCards_ShouldThrowException() {
    GameCardEntity firstCard = cardAt(0);
    board.setGameCards(List.of(firstCard));

    assertThatThrownBy(() -> lineValidationService.validateLine(board, firstCard, player))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("16 cartes");
  }

  private GameCardEntity cardAt(int index) {
    return board.getGameCards().get(index);
  }

  private void ownCardsAtLevelOne(int... indexes) {
    for (int index : indexes) {
      cardAt(index).setOwnerPointLvl1(player);
    }
  }

  private void ownCardsAtLevelTwo(int... indexes) {
    for (int index : indexes) {
      cardAt(index).setOwnerPointLvl2(player);
    }
  }
}
