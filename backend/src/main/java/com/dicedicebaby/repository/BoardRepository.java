package com.dicedicebaby.repository;

import com.dicedicebaby.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {}
