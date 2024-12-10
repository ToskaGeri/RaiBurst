package com.raiburst.demo.Repository;


import com.raiburst.demo.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
