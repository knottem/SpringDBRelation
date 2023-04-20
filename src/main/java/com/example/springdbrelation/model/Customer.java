package com.example.springdbrelation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String address;

    @Column
    private String ssn;

    public Customer(String firstName, String lastName, String address, String ssn){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.ssn = ssn;
    }

    @OneToOne
    @JoinColumn
    private CPI cpi;
}
