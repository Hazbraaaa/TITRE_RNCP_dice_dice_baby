package com.dicedicebaby.repository;

import com.dicedicebaby.entity.DiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiceRepository extends JpaRepository<DiceEntity, Long> {}
