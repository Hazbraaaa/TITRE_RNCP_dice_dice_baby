package com.dicedicebaby.repository;

import com.dicedicebaby.entity.GameCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCardRepository extends JpaRepository<GameCardEntity, Long> {}
