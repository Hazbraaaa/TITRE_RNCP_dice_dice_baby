package com.dicedicebaby.service;

import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    //region Attributes
    private final PlayerRepository playerRepository;
    //endregion

    //region Constructor
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    //endregion

    //region Methods
    @Transactional
    public PlayerEntity createPlayerForAccount(AccountEntity account, int playerNumber) {
        // Create a player from an account
        PlayerEntity player = new PlayerEntity();

        player.setPlayerUsername(account.getUsername());
        player.setIsGuest(false);
        player.setAccount(account);
        player.setScore(0);
        player.setPlayerNumber(playerNumber);

        // Return saved player in database
        return playerRepository.save(player);
    }

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
    //endregion
}