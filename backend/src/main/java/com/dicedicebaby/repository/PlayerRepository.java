package com.dicedicebaby.repository;

import com.dicedicebaby.entity.PlayerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

  PlayerEntity findByPlayerUsername(String playerUsername);

  List<PlayerEntity> findByIsGuestTrue();

  boolean existsByPlayerUsername(String playerUsername);

  PlayerEntity findByAccountId(Long accountId);
}
