package com.dicedicebaby.mapper;

import com.dicedicebaby.dto.response.*;
import com.dicedicebaby.entity.*;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
  public GameResponseDTO mapToGameResponseDTO(GameEntity game) {
    List<CardResponseDTO> boardDTO = mapToBoardResponseDTO(game.getBoard());
    DiceSetResponseDTO diceSetDTO = mapToDiceSetResponseDTO(game.getDiceSet());
    PlayerInGameResponseDTO currentPlayerDTO = mapToPlayerInGameDTO(game.getCurrentPlayer());
    List<PlayerInGameResponseDTO> playersDTO =
        game.getPlayers().stream().map(this::mapToPlayerInGameDTO).toList();

    return new GameResponseDTO(
        game.getId(),
        game.getState(),
        game.getRollsLeft(),
        game.getRoundNumber(),
        boardDTO,
        diceSetDTO,
        currentPlayerDTO,
        playersDTO);
  }

  private List<CardResponseDTO> mapToBoardResponseDTO(BoardEntity board) {
    return board.getGameCards().stream()
        .map(
            gameCard -> {
              CardEntity card = gameCard.getCard();

              // Check if owner is null
              Integer owner1 =
                  (gameCard.getOwnerPointLvl1() != null)
                      ? gameCard.getOwnerPointLvl1().getPlayerNumber()
                      : null;

              Integer owner2 =
                  (gameCard.getOwnerPointLvl2() != null)
                      ? gameCard.getOwnerPointLvl2().getPlayerNumber()
                      : null;

              return new CardResponseDTO(
                  gameCard.getId(),
                  card.getCombination(),
                  card.getColor(),
                  card.getPointLvl1(),
                  card.getPointLvl2(),
                  owner1,
                  owner2);
            })
        .toList();
  }

  private DiceSetResponseDTO mapToDiceSetResponseDTO(DiceSetEntity diceSet) {
    List<DiceResponseDTO> dicesList =
        diceSet.getDices().stream()
            .map(dice -> new DiceResponseDTO(dice.getId(), dice.getValue(), dice.isKept()))
            .toList();

    return new DiceSetResponseDTO(diceSet.getId(), dicesList);
  }

  private PlayerInGameResponseDTO mapToPlayerInGameDTO(PlayerEntity player) {
    if (player == null) return null;

    return new PlayerInGameResponseDTO(
        player.getId(), player.getPlayerUsername(), player.getScore(), player.getRemainingChips());
  }
}
