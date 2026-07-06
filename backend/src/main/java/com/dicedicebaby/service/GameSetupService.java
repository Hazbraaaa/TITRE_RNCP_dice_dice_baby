package com.dicedicebaby.service;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.dto.response.*;
import com.dicedicebaby.entity.*;
import com.dicedicebaby.mapper.GameMapper;
import com.dicedicebaby.repository.CardRepository;
import com.dicedicebaby.repository.GameRepository;
import com.dicedicebaby.repository.PlayerRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameSetupService {
  // region Attributes
  private final CardRepository cardRepository;
  private final PlayerRepository playerRepository;
  private final GameRepository gameRepository;
  private final GameMapper gameMapper;

  // endregion

  // region Constructor
  public GameSetupService(
      CardRepository cardRepository,
      PlayerRepository playerRepository,
      GameRepository gameRepository,
      GameMapper gameMapper) {
    this.cardRepository = cardRepository;
    this.playerRepository = playerRepository;
    this.gameRepository = gameRepository;
    this.gameMapper = gameMapper;
  }

  // endregion

  // region Methods
  @Transactional
  public GameResponseDTO setupNewGame(String existingCookie) {
    // Create a game
    GameEntity game = new GameEntity();

    game = gameRepository.saveAndFlush(game);

    // Get players from cookie in game
    List<PlayerEntity> players = extractPlayersFromCookie(existingCookie);

    // Set game in every players, and reset score and remaining chips
    for (PlayerEntity player : players) {
      player.setGame(game);
      player.setScore(Constant.GameData.INITIAL_POINTS);
      player.setRemainingChips(Constant.GameData.INITIAL_CHIPS);
    }

    game.setPlayers(players);

    // Set current player
    if (!players.isEmpty()) {
      game.setCurrentPlayer(players.getFirst());
    }

    // Set a board with random card in game
    game.setBoard(createBoard(game));

    // Set a dice set in game
    game.setDiceSet(createDiceSet(game));

    // Save game in base
    gameRepository.save(game);

    return gameMapper.mapToGameResponseDTO(game);
  }

  private List<PlayerEntity> extractPlayersFromCookie(String existingCookie) {
    // Get tokens from cookie
    List<String> tokens = new ArrayList<>(Arrays.asList(existingCookie.split(Constant.SEPARATOR)));

    // Create players list to return
    List<PlayerEntity> players = new ArrayList<>();

    // Get players from tokens
    for (String token : tokens) {
      players.add(playerRepository.findByCurrentJwt(token));
    }

    // Return players
    return players;
  }

  private BoardEntity createBoard(GameEntity game) {
    BoardEntity board = new BoardEntity();
    board.setGame(game);

    // Get random card from official list
    List<CardEntity> randomCards = cardRepository.findRandomCards();

    // Transform them in game cards
    List<GameCardEntity> gameCards =
        randomCards.stream()
            .map(
                card -> {
                  GameCardEntity gameCard = new GameCardEntity();
                  gameCard.setCard(card);
                  gameCard.setBoard(board);
                  gameCard.setOwnerPointLvl1(null);
                  gameCard.setOwnerPointLvl2(null);
                  return gameCard;
                })
            .collect(Collectors.toList());

    // Link game cards to board
    board.setGameCards(gameCards);

    return board;
  }

  private DiceSetEntity createDiceSet(GameEntity game) {
    DiceSetEntity diceSet = new DiceSetEntity();
    diceSet.setGame(game);

    List<DiceEntity> dices = new ArrayList<>();
    for (int i = 0; i < Constant.GameData.DICE_COUNT; i++) {
      DiceEntity dice = new DiceEntity();
      dice.setValue(Constant.GameData.MAX_DICE_VALUE);
      dice.setKept(false);
      dice.setDiceSet(diceSet);
      dices.add(dice);
    }
    diceSet.setDices(dices);

    return diceSet;
  }
  // endregion
}
