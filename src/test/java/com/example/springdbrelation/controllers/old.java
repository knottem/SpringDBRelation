package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.CPI;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class old {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepo;

    @BeforeEach
    void setUp() {
        customerRepo.deleteAll();
        customerRepo.save(new Customer("John", "Doe", "123 Main St", "123-45-6789", new CPI(30)));
        customerRepo.save(new Customer("Jane", "Doe", "123 Main St", "123-45-6789", new CPI(30)));
    }

    @Test
    public void getHello() throws Exception {
        mockMvc.perform(get("/test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    public void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void addNewCustomer() throws Exception {
        mockMvc.perform(post("/newCustomer2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper()
                        .writeValueAsString(
                                new Customer("Test", "Mannen", "123 Main St", "123-45-6789", new CPI(30)))))
                .andExpect(status().isOk())
                .andExpect(content().string("New customer added: Test Mannen"));

        mockMvc.perform(get("/customer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void addNewCustomerFaultyInput() throws Exception {
        mockMvc.perform(post("/newCustomer2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer is null or some of the fields are null"));
    }
}