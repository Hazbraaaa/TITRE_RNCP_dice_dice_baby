package com.dicedicebaby.repository;

import com.dicedicebaby.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    PlayerEntity findByUsername(String username);

    List<PlayerEntity> findByIsGuestTrue();
}
