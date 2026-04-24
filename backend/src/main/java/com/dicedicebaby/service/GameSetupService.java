package com.dicedicebaby.service;

import com.dicedicebaby.entity.CardEntity;
import com.dicedicebaby.repository.CardRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameSetupService {
  // region Attributes
  private final CardRepository cardRepository;

  // endregion

  // region Constructor
  public GameSetupService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  // endregion

  // region Methods
  @Transactional
  public List<CardEntity> getBoard() {
    return cardRepository.findRandomCards();
  }
  // endregion
}
