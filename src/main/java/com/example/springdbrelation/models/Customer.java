package com.example.springdbrelation.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private CPI cpi;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private Category category;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private List<Account> accounts = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String address;
    private String ssn;

    public Customer(String firstName, String lastName, String address, String ssn, CPI cpi){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.ssn = ssn;
        this.cpi = cpi;
    }

    public Customer(String firstName, String lastName, String address, String ssn, CPI cpi, Category category){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.ssn = ssn;
        this.cpi = cpi;
        this.category = category;
    }

    public Customer(String firstName, String lastName, String address, String ssn, CPI cpi, Category category, List<Account> accounts){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.ssn = ssn;
        this.cpi = cpi;
        this.category = category;
        this.accounts = accounts;
    }

}