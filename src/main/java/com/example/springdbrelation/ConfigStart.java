package com.example.springdbrelation;

import com.example.springdbrelation.models.Account;
import com.example.springdbrelation.models.CPI;
import com.example.springdbrelation.models.Category;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.AccountRepository;
import com.example.springdbrelation.repositories.CategoryRepository;
import com.example.springdbrelation.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ConfigStart {

    @Value("${config.start:false}")
    private boolean configStart;

    private static final Logger logger = LoggerFactory.getLogger(ConfigStart.class);

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepo,
                                  AccountRepository accountRepository,
                                  CategoryRepository categoryRepository) {
        return (args) -> {
                logger.info("ConfigStart: " + configStart);
                if(configStart) {

                    logger.info("Running ConfigStart: adding data");

                    Category categoryGold = new Category("Gold");
                    Category categorySilver = new Category("Silver");
                    Category categoryBronze = new Category("Bronze");

                    categoryRepository.save(categoryGold);
                    categoryRepository.save(categorySilver);
                    categoryRepository.save(categoryBronze);

                    Account account = new Account(1000, 1.5);
                    Account account1 = new Account(2000, 1.5);
                    Account account2 = new Account(500, 1.5);
                    Account account3 = new Account(5000, 1.5);

                    accountRepository.save(account);
                    accountRepository.save(account1);
                    accountRepository.save(account2);
                    accountRepository.save(account3);

                    customerRepo.save(new Customer(
                            "Harry",
                            "Potter",
                            "Gringotts",
                            "8505011234",
                            new CPI(32),
                            categoryGold,
                            new ArrayList<>(List.of(
                                    account,
                                    account1
                            ))));

                    customerRepo.save(new Customer(
                            "John",
                            "Doe",
                            "123 Main St",
                            "9905011234",
                            new CPI(100),
                            categorySilver,
                            new ArrayList<>(List.of(
                                    account2))));

                    customerRepo.save(new Customer(
                            "Jane",
                            "Doe",
                            "123 Main St",
                            "8905011234",
                            new CPI(76),
                            categoryBronze,
                            new ArrayList<>(List.of(
                                    account3))));

                    customerRepo.save(new Customer(
                            "John",
                            "Smith",
                            "123 Main St",
                            "9905011234",
                            new CPI(22),
                            categoryGold,
                            new ArrayList<>(List.of(
                                    account,
                                    account1,
                                    account2,
                                    account3))));

                }
        };
    }
}
