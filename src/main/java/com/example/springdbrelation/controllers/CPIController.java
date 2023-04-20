package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.CPI;
import com.example.springdbrelation.repositories.CPIRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CPIController {

    private final CPIRepository cpiRepo;

    public CPIController(CPIRepository cpiRepo) {
        this.cpiRepo = cpiRepo;
    }

    @RequestMapping("/cpiAll")
    public List<CPI> getAllCPI() {
        return cpiRepo.findAll();
    }
}
