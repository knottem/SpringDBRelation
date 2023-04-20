package com.example.springdbrelation.repositories;

import com.example.springdbrelation.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
