package com.example.springdbrelation;

import com.example.springdbrelation.models.Account;
import com.example.springdbrelation.models.CPI;
import com.example.springdbrelation.models.Category;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.AccountRepository;
import com.example.springdbrelation.repositories.CategoryRepository;
import com.example.springdbrelation.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringDbRelationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDbRelationApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepo,
                                  CategoryRepository categoryRepo,
                                  AccountRepository accountRepo) {
        return (args) -> {

            Category category1 = new Category("Gold");
            Category category2 = new Category("Silver");
            Category category3 = new Category("Bronze");

            categoryRepo.save(category1);
            categoryRepo.save(category2);
            categoryRepo.save(category3);

            Account account1 = new Account(1000, 1.5);
            Account account2 = new Account(5000, 1.5);
            Account account3 = new Account(10000, 1.5);
            Account account4 = new Account(20000, 1.5);

            accountRepo.save(account1);
            accountRepo.save(account2);
            accountRepo.save(account3);
            accountRepo.save(account4);


            Customer customer1 = new Customer(
                    "Harry",
                    "Potter",
                    "Gringotts",
                    "8505011234",
                    new CPI(32));

            Customer customer2 = new Customer(
                    "John",
                    "Doe",
                    "123 Main St",
                    "9905011234",
                    new CPI(100));

            Customer customer3 = new Customer(
                    "Jane",
                    "Doe",
                    "123 Main St",
                    "8905011234",
                    new CPI(76));

            Customer customer4 = new Customer(
                    "John",
                    "Smith",
                    "123 Main St",
                    "9905011234",
                    new CPI(22));

            customer1.setCategory(category1);
            customer2.setCategory(category2);
            customer3.setCategory(category3);
            customer4.setCategory(category1);

            customer1.getAccounts().add(account1);
            customer1.getAccounts().add(account2);
            customer2.getAccounts().add(account3);
            customer3.getAccounts().add(account4);
            customer4.getAccounts().addAll(List.of(account1, account2, account3, account4));

            customerRepo.save(customer1);
            customerRepo.save(customer2);
            customerRepo.save(customer3);
            customerRepo.save(customer4);


        };


    }

}
