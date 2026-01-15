package com.dicedicebaby.service;

import com.dicedicebaby.entity.AccountEntity;
import com.dicedicebaby.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AccountService {

    //region Attributes
    private final AccountRepository accountRepository;
    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;
    //endregion

    //region Constructor
    public AccountService(AccountRepository accountRepository, PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }
    //endregion

    @Transactional
    public AccountEntity registerNewAccount(String username, String email, String password) {
        // Check unique account
        if (accountRepository.existsByEmail(email)) {
            throw new IllegalStateException("L'email est déjà utilisé.");
        }
        if (accountRepository.findByUsername(username) != null) {
            throw new IllegalStateException("Le nom est déjà utilisé.");
        }

        // Create new AccountEntity
        AccountEntity newAccount = new AccountEntity();
        newAccount.setUsername(username);
        newAccount.setEmail(email);
        newAccount.setPasswordHash(passwordEncoder.encode(password));

        // Save new AccountEntity
        AccountEntity savedAccount = accountRepository.save(newAccount);

        return savedAccount;
    }
}
