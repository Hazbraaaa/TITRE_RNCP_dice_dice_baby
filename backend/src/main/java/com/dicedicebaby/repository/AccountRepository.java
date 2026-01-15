package com.dicedicebaby.repository;

import com.dicedicebaby.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByEmail(String email);

    AccountEntity findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
