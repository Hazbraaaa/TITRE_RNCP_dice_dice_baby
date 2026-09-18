package com.dicedicebaby.service;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.entity.BoardEntity;
import com.dicedicebaby.entity.GameCardEntity;
import com.dicedicebaby.entity.PlayerEntity;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class LineValidationService {

  // region Methods
  /**
   * Checks whether the validated card completes a row, column or diagonal owned by the player. A
   * card belongs to the player when either of its two point slots contains one of their chips.
   *
   * @param boardEntity the board containing the cards
   * @param validatedCard the card that has just been validated
   * @param player the player who validated the card
   * @return {@code true} if the validated card completes a line for the player
   * @throws IllegalArgumentException if the board, card or player is invalid
   */
  public boolean validateLine(
      BoardEntity boardEntity, GameCardEntity validatedCard, PlayerEntity player) {

    // Check that all required data is provided
    if (boardEntity == null || validatedCard == null || player == null) {
      throw new IllegalArgumentException("Le plateau, la carte et le joueur sont obligatoires.");
    }

    // Retrieve all cards from the board
    List<GameCardEntity> gameCards = boardEntity.getGameCards();

    // Check that the board contains the expected board card count
    if (gameCards == null || gameCards.size() != Constant.GameData.BOARD_CARD_COUNT) {
      throw new IllegalArgumentException(
          "Le plateau doit contenir exactement " + Constant.GameData.BOARD_CARD_COUNT + " cartes.");
    }

    // Find the position of the card that has just been validated
    int validatedCardIndex = findCardIndex(gameCards, validatedCard);

    // Check that the validated card belongs to this board
    if (validatedCardIndex < 0) {
      throw new IllegalArgumentException("La carte validée n'appartient pas au plateau.");
    }

    // Calculate the row and column of the validated card
    int boardSize = Constant.GameData.BOARD_SIZE;
    int row = validatedCardIndex / boardSize;
    int column = validatedCardIndex % boardSize;

    // Check every card in the corresponding row
    boolean hasCompletedRow =
        IntStream.range(0, boardSize)
            .allMatch(index -> isOwnedBy(gameCards.get(row * boardSize + index), player));

    // Check every card in the corresponding column
    boolean hasCompletedColumn =
        IntStream.range(0, boardSize)
            .allMatch(index -> isOwnedBy(gameCards.get(index * boardSize + column), player));

    // Check the main diagonal, from top-left to bottom-right
    // This check is only required if the validated card belongs to this diagonal
    boolean hasCompletedMainDiagonal =
        row == column
            && IntStream.range(0, boardSize)
                .allMatch(index -> isOwnedBy(gameCards.get(index * boardSize + index), player));

    // Check the anti-diagonal, from top-right to bottom-left
    // This check is only required if the validated card belongs to this diagonal
    boolean hasCompletedAntiDiagonal =
        row + column == boardSize - 1
            && IntStream.range(0, boardSize)
                .allMatch(
                    index ->
                        isOwnedBy(
                            gameCards.get(index * boardSize + (boardSize - 1 - index)), player));

    // A line is completed if at least one of the four directions is complete
    return hasCompletedRow
        || hasCompletedColumn
        || hasCompletedMainDiagonal
        || hasCompletedAntiDiagonal;
  }

  private int findCardIndex(List<GameCardEntity> gameCards, GameCardEntity validatedCard) {
    // Search for the card index and return it
    for (int index = 0; index < gameCards.size(); index++) {
      GameCardEntity boardCard = gameCards.get(index);
      if (boardCard == validatedCard
          || (boardCard != null
              && boardCard.getId() != null
              && Objects.equals(boardCard.getId(), validatedCard.getId()))) {
        return index;
      }
    }
    // No card index found
    return -1;
  }

  private boolean isOwnedBy(GameCardEntity gameCard, PlayerEntity player) {
    return gameCard != null
        && (isSamePlayer(gameCard.getOwnerPointLvl1(), player)
            || isSamePlayer(gameCard.getOwnerPointLvl2(), player));
  }

  private boolean isSamePlayer(PlayerEntity owner, PlayerEntity player) {
    return owner == player
        || (owner != null
            && owner.getId() != null
            && Objects.equals(owner.getId(), player.getId()));
  }
  // endregion
}
