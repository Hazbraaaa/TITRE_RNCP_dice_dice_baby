package com.dicedicebaby.repository;

import com.dicedicebaby.entity.CardEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
  @Query(value = "SELECT * FROM cards ORDER BY RANDOM() LIMIT 16", nativeQuery = true)
  List<CardEntity> findRandomCards();
}
