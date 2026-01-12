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

    @Transactional
    public PlayerEntity createPlayerForAccount(AccountEntity account) {
        PlayerEntity player = new PlayerEntity();
        player.setUsername(account.getUsername());
        player.setIsGuest(false);
        player.setAccount(account);
        player.setScore(0);

        return playerRepository.save(player);
    }
}
