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
    private final PasswordEncoder passwordEncoder;
    //endregion

    //region Constructor
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //endregion

    //region Methods
    @Transactional
    public AccountEntity registerNewAccount(String username, String email, String password) {
        // Check unique account
        if (accountRepository.existsByEmail(email)) {
            throw new IllegalStateException("L'Email est déjà utilisé.");
        }
        if (accountRepository.findByUsername(username) != null) {
            throw new IllegalStateException("Le nom est déjà utilisé.");
        }

        // Create new account
        AccountEntity newAccount = new AccountEntity();
        newAccount.setUsername(username);
        newAccount.setEmail(email);
        newAccount.setPasswordHash(passwordEncoder.encode(password));

        // Return saved account in database
        return accountRepository.save(newAccount);
    }
    //endregion
}
