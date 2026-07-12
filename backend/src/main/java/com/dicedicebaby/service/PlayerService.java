package com.dicedicebaby.service;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

  // region Attributes
  private final PlayerRepository playerRepository;

  // endregion

  // region Constructor
  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  // endregion

  // region Methods
  /**
   * Creates and saves a player linked to an account.
   *
   * @param account the player account
   * @param playerNumber the player's position
   * @return the saved player
   */
  @Transactional
  public PlayerEntity createPlayerForAccount(AccountEntity account, int playerNumber) {
    // Create a player from an account
    PlayerEntity player = new PlayerEntity();

    player.setPlayerUsername(account.getUsername());
    player.setIsGuest(false);
    player.setAccount(account);
    player.setScore(Constant.GameData.INITIAL_POINTS);
    player.setPlayerNumber(playerNumber);

    // Return saved player in database
    return playerRepository.save(player);
  }

  /**
   * Finds the player linked to an account.
   *
   * @param account the account to search for
   * @return the associated player
   * @throws RuntimeException if no player is found
   */
  @Transactional
  public PlayerEntity getPlayerByAccount(AccountEntity account) {
    // Get player from an account
    PlayerEntity player = playerRepository.findByAccountId(account.getId());

    // Check if player found
    if (player == null) {
      throw new RuntimeException("Profil joueur introuvable");
    }

    // Return player from database
    return player;
  }

  /**
   * Finds a player by username.
   *
   * @param playerUsername the username to search for
   * @return the matching player
   * @throws RuntimeException if no player is found
   */
  @Transactional
  public PlayerEntity getPlayerByPlayerUsername(String playerUsername) {
    // Get player from a username
    PlayerEntity player = playerRepository.findByPlayerUsername(playerUsername);

    // Check if player found
    if (player == null) {
      throw new RuntimeException("Profil joueur introuvable");
    }

    // Return player from database
    return player;
  }

  /**
   * Finds a player by their current token.
   *
   * @param token the authentication token
   * @return the matching player
   * @throws RuntimeException if no player is found
   */
  @Transactional
  public PlayerEntity getPlayerByToken(String token) {
    // Get player from a username
    PlayerEntity player = playerRepository.findByCurrentJwt(token);

    // Check if player found
    if (player == null) {
      throw new RuntimeException("Profil joueur introuvable");
    }

    // Return player from database
    return player;
  }

  /**
   * Creates and saves a guest player.
   *
   * @param username the guest username
   * @param playerNumber the player's position
   * @return the saved guest player
   * @throws IllegalStateException if the username is already used
   */
  @Transactional
  public PlayerEntity createPlayerForGuest(String username, int playerNumber) {
    // Create a player for a guest
    PlayerEntity player = new PlayerEntity();

    player.setPlayerUsername(username);
    player.setIsGuest(true);
    player.setAccount(null);
    player.setScore(Constant.GameData.INITIAL_POINTS);
    player.setPlayerNumber(playerNumber);

    // Return saved player in database
    return playerRepository.save(player);
  }
  // endregion
}
