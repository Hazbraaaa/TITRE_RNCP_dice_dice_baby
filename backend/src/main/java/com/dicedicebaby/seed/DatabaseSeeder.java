package com.dicedicebaby.seed;

import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAccounts();
    }

    private void seedAccounts() {
        // Check if there is data in Database
        if (accountRepository.count() == 0) {
            // Create userOne
            AccountEntity userOne = new AccountEntity();
            userOne.setUsername("userOne");
            userOne.setEmail("userOne@dicedicebaby.com");
            userOne.setPasswordHash(passwordEncoder.encode("userOne123"));

            accountRepository.save(userOne);

            // Create userTwo
            AccountEntity userTwo = new AccountEntity();
            userTwo.setUsername("userTwo");
            userTwo.setEmail("userTwo@example.com");
            userTwo.setPasswordHash(passwordEncoder.encode("userTwo123"));

            accountRepository.save(userTwo);

            System.out.println("Seeding terminé: Comptes 'userOne' et 'userTwo' créés.");
        }
        else {
            System.out.println("Seeding ignoré: Des comptes existent déjà en base.");
        }
    }
}