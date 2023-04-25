package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.Account;
import com.example.springdbrelation.models.CPI;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.AccountRepository;
import com.example.springdbrelation.repositories.CPIRepository;
import com.example.springdbrelation.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/customer")
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @RequestMapping("/customer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable long id){
        return customerRepo.findById(id);
    }

    @RequestMapping("/customer/firstName/{firstName}")
    public List<Customer> getCustomerByFirstName(@PathVariable String firstName) {
        return customerRepo.findByFirstName(firstName);
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
        if(customer.getFirstName() == null || customer.getLastName()  == null || customer.getAddress() == null || customer.getSsn() == null || customer.getCpi() == null) {
            return "Customer is null or some of the fields are null";
        }
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
            /*
            if(customer.getAccounts().stream().anyMatch(a -> a.getId() == accountId)){
                Account account = accountRepo.findById(accountId).isPresent() ? accountRepo.findById(accountId).get() : null;
                if(account != null){
                    account.setBalance(balance);
                    accountRepo.save(account);
                }
            }
             */
            customer.getAccounts().stream().filter(a -> a.getId() == accountId).forEach(a -> a.setBalance(balance));
            customerRepo.save(customer);
            return "Balance changed to " + balance + " for account " + accountId + " for customer " + customer.getFirstName() + " " + customer.getLastName();
        } else {
            return "Wrong Customer Id";
        }
    }

    @RequestMapping("/customer/{id}/addExistingAccount/{accountId}")
    public String addExistingAccount(@PathVariable long id,
                                     @PathVariable long accountId) {
        Customer customer = customerRepo.findById(id).isPresent() ? customerRepo.findById(id).get() : null;
        if(customer != null){
            Account account = accountRepo.findById(accountId).isPresent() ? accountRepo.findById(accountId).get() : null;
            if(account != null){
                if(customer.getAccounts().stream().anyMatch(a -> a.getId() == accountId)){
                    return "Account " + accountId + " already exists for " + customer.getFirstName() + " " + customer.getLastName();
                }
                customer.getAccounts().add(account);
                customerRepo.save(customer);
                return "Account " + accountId + " added to " + customer.getFirstName() + " " + customer.getLastName();
            } else {
                return "Wrong Account Id";
            }
        } else {
            return "Wrong Customer Id";
        }
    }

    @RequestMapping("/customer/{id}/addAccount")
    public String addAccountCustomer(@PathVariable long id) {
        Customer customer = customerRepo.findById(id).isPresent() ? customerRepo.findById(id).get() : null;
        if(customer != null){
            customer.getAccounts().add(new Account(1000, 1.5));
            customerRepo.save(customer);
            return "New Account added to " + customer.getFirstName() + " " + customer.getLastName();
        } else {
            return "Wrong Id";
        }
    }
}
