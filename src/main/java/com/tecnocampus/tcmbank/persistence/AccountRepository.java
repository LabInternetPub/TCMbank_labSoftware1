package com.tecnocampus.tcmbank.persistence;

import com.tecnocampus.tcmbank.domain.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findAllByUserIdEquals(String userId);

    @Transactional
    void removeAllByUserId(String userId);
}