package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.Account;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.AccountRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    private final AccountRepository accountRepo;

    public AccountController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @RequestMapping("/accountAll")
    private List<Account> getAccounts() {
        return accountRepo.findAll();
    }
}
