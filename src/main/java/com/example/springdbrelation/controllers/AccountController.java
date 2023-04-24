package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.Account;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.AccountRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class AccountController {

    private final AccountRepository accountRepo;

    public AccountController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @RequestMapping({"/accountAll", "/account"})
    private List<Account> getAccounts() {
        return accountRepo.findAll();
    }

    @RequestMapping("/accountNew/{balance}/{rate}")
    private Account addNewAccount(@PathVariable double balance,
                                  @PathVariable double rate) {
        Account account = new Account(balance, rate);
        accountRepo.save(account);
        return account;
    }

    @RequestMapping("/account/{id}")
    private Account getAccountById(@PathVariable long id) {
        return accountRepo.findById(id).isPresent() ? accountRepo.findById(id).get() : null;
    }

    @RequestMapping("/account/{id}/changeBalance/{balance}")
    private Account getAccountCustomer(@PathVariable long id,
                                        @PathVariable double balance) {
        Account account = accountRepo.findById(id).isPresent() ? accountRepo.findById(id).get() : null;
        if (account != null) {
            account.setBalance(balance);
            accountRepo.save(account);
        }
        return account;
    }

    @RequestMapping("/accounts/{id}/changeRate/{rate}")
    private Account getAccountRate(@PathVariable long id,
                                   @PathVariable double rate) {
        Account account = accountRepo.findById(id).isPresent() ? accountRepo.findById(id).get() : null;
        if (account != null) {
            account.setInterestRate(rate);
            accountRepo.save(account);
        }
        return account;
    }

}
