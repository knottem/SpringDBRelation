package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.Account;
import com.example.springdbrelation.models.CPI;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.AccountRepository;
import com.example.springdbrelation.repositories.CPIRepository;
import com.example.springdbrelation.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@RestController()
public class CustomerController {

    private final CustomerRepository customerRepo;
    private final CPIRepository cpiRepo;
    private final AccountRepository accountRepo;

    public CustomerController(CustomerRepository customerRepo,
                              CPIRepository cpiRepo,
                              AccountRepository accountRepo) {
        this.customerRepo = customerRepo;
        this.cpiRepo = cpiRepo;
        this.accountRepo = accountRepo;
    }

    @RequestMapping("/customerAll")
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @RequestMapping("/customer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable long id){
        return customerRepo.findById(id);
    }

    @RequestMapping("/newCustomer")
    public String addNewCustomer(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String address,
                                 @RequestParam String ssn,
                                 @RequestParam int credit) {
        CPI cpi = new CPI(credit);
        cpiRepo.save(cpi);
        Customer customer = new Customer(firstName, lastName, address, ssn, cpi);
        customerRepo.save(customer);
        return "New customer added: " + firstName + " " + lastName;
    }

    @RequestMapping("/newCustomer2")
    public String addNewCustomer2(@RequestBody Customer customer) {
        customerRepo.save(customer);
        return "New customer added: " + customer.getFirstName() + " " + customer.getLastName();
    }

    @RequestMapping("/customer/{id}/accounts")
    public List<Account> getCustomerAccounts(@PathVariable long id) {
        return customerRepo.findById(id).isPresent() ? customerRepo.findById(id).get().getAccounts() : null;
    }

    @RequestMapping("/customer/{id}/accounts/{accountId}/changeBalance/{balance}")
    public String changeAccountBalance(@PathVariable long id,
                                       @PathVariable long accountId,
                                       @PathVariable double balance) {
        Customer customer = customerRepo.findById(id).isPresent() ? customerRepo.findById(id).get() : null;
        if (customer != null) {
            if (customer.getAccounts().stream().anyMatch(a -> a.getId() == accountId)) {
                Account account = accountRepo.findById(accountId).isPresent() ? accountRepo.findById(accountId).get() : null;
                if (account != null) {
                    account.setBalance(balance);
                    accountRepo.save(account);
                    return "Balance changed to " + balance;
                } else {
                    return "Wrong Account Id";
                }
            }
        } else {
            return "Wrong Customer Id";
        }
        return "bör inte komma hit";
    }

    @RequestMapping("/customer/{id}/addAccount")
    public String addAccountCustomer(@PathVariable long id) {
        Customer customer = customerRepo.findById(id).isPresent() ? customerRepo.findById(id).get() : null;
        if(customer != null){
            Account account = new Account(1000, 1.5);
            accountRepo.save(account);
            customer.getAccounts().add(account);
            customerRepo.save(customer);
            return "New Account added to " + customer.getFirstName() + " " + customer.getLastName();
        } else {
            return "Wrong Id";
        }
    }
}