package com.dicedicebaby.repository;

import com.dicedicebaby.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    PlayerEntity findByPlayerUsername(String playerUsername);

    List<PlayerEntity> findByIsGuestTrue();

    boolean existsByPlayerUsername(String playerUsername);
}
