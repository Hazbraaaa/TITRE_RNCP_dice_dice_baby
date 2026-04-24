package com.dicedicebaby.seed;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.CardEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.repository.AccountRepository;
import com.dicedicebaby.repository.CardRepository;
import com.dicedicebaby.repository.PlayerRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

  // region Attributes
  private final AccountRepository accountRepository;
  private final PlayerRepository playerRepository;
  private final CardRepository cardRepository;
  private final PasswordEncoder passwordEncoder;

  // endregion

  // region Constructor
  public DatabaseSeeder(
      AccountRepository accountRepository,
      PlayerRepository playerRepository,
      CardRepository cardRepository,
      PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.playerRepository = playerRepository;
    this.cardRepository = cardRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // endregion

  // region Methods
  @Override
  public void run(String... args) throws Exception {
    seedAccounts();
    seedCards();
  }

  private void seedAccounts() {
    // Check if there is data in Database
    if (accountRepository.count() == 0) {
      // Create test account
      AccountEntity testAccount = new AccountEntity();
      testAccount.setUsername("test");
      testAccount.setEmail("test@test.fr");
      testAccount.setPasswordHash(passwordEncoder.encode("Test!123"));

      accountRepository.save(testAccount);

      // Create test player
      PlayerEntity testPlayer = new PlayerEntity();
      testPlayer.setPlayerUsername(testAccount.getUsername());
      testPlayer.setIsGuest(false);
      testPlayer.setAccount(testAccount);
      testPlayer.setScore(0);

      playerRepository.save(testPlayer);

      System.out.println("Seeding terminé: Compte et Joueur 'test' créés.");
    } else {
      System.out.println("Seeding ignoré: Des comptes existent déjà en base.");
    }
  }

  private void seedCards() {
    // Check if there is data in Database
    if (cardRepository.count() == 0) {
      // Create cards
      List<CardEntity> entities =
          Constant.GameData.DEFAULT_CARDS.stream()
              .map(
                  CardData -> {
                    CardEntity entity = new CardEntity();
                    entity.setCombination(CardData.combination());
                    entity.setColor(CardData.color());
                    entity.setPointLvl1(CardData.ptLvl1());
                    entity.setPointLvl2(CardData.ptLvl2());
                    return entity;
                  })
              .toList();

      cardRepository.saveAll(entities);

      System.out.println("Seeding terminé: Cartes créés.");
    } else {
      System.out.println("Seeding ignoré: Des cartes existent déjà en base.");
    }
  }
  // endregion
}
