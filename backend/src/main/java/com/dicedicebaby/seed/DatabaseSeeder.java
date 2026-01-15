package com.dicedicebaby.seed;

import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.entity.PlayerEntity;
import com.dicedicebaby.repository.AccountRepository;
import com.dicedicebaby.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    //region Attributes
    private final AccountRepository accountRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    //endregion

    //region Constructor
    public DatabaseSeeder(AccountRepository accountRepository, PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //endregion

    //region Methods
    @Override
    public void run(String... args) throws Exception {
        seedAccounts();
    }

    private void seedAccounts() {
        // Check if there is data in Database
        if (accountRepository.count() == 0) {
            // Create test account
            AccountEntity testAccount = new AccountEntity();
            testAccount.setUsername("test");
            testAccount.setEmail("test@test.fr");
            testAccount.setPasswordHash(passwordEncoder.encode("test123"));

            accountRepository.save(testAccount);

            // Create test player
            PlayerEntity testPlayer = new PlayerEntity();
            testPlayer.setPlayerUsername(testAccount.getUsername());
            testPlayer.setIsGuest(false);
            testPlayer.setAccount(testAccount);
            testPlayer.setScore(0);

            playerRepository.save(testPlayer);

            System.out.println("Seeding terminé: Compte et Joueur 'test' créés.");
        }
        else {
            System.out.println("Seeding ignoré: Des comptes existent déjà en base.");
        }
    }
    //endregion
}