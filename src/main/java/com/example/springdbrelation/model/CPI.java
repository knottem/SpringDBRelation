package com.example.springdbrelation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CPI {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String creditworthiness;

    public CPI(String creditworthiness) {
        this.creditworthiness = creditworthiness;
    }
}
