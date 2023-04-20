package com.example.springdbrelation.models;

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

    private int creditworthiness;

    public CPI(int creditworthiness) {
        this.creditworthiness = creditworthiness;
    }
}
