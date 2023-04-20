package com.example.springdbrelation.repositories;

import com.example.springdbrelation.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
