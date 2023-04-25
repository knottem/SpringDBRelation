package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.Account;
import com.example.springdbrelation.models.CPI;
import com.example.springdbrelation.models.Customer;
import com.example.springdbrelation.repositories.AccountRepository;
import com.example.springdbrelation.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository mockRepo;

    @MockBean
    private AccountRepository mockAccountRepo;

    @BeforeEach
    public void setUp() {
        Customer customer = new Customer(
                "John",
                "Doe",
                "123 Main St",
                "123-45-6789",
                new CPI(30));
        Customer customer1 = new Customer(
                "Jane",
                "Doe",
                "123 Main St",
                "123-45-6789",
                new CPI(30));
        Customer customer2 = new Customer(
                "Peter",
                "Doe",
                "123 Main St",
                "123-45-6789",
                new CPI(30));

        Account account = new Account(1000, 1.5);

        when(mockAccountRepo.findById(1L)).thenReturn(Optional.of(account));

        when(mockRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(customer1));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(customer2));

        when(mockRepo.findByFirstName("John")).thenReturn(List.of(customer));
        when(mockRepo.findByFirstName("Jane")).thenReturn(List.of(customer1));
        when(mockRepo.findByFirstName("Peter")).thenReturn(List.of(customer2));

        when(mockRepo.findAll()).thenReturn(List.of(customer, customer1, customer2));
    }


    @Test
    void testTest() throws Exception {
       this.mockMvc.perform(get("/test"))
               .andExpect(status().isOk())
               .andExpect(content().string("test"));
    }

    @Test
    void testGetCustomerId() throws Exception {
        this.mockMvc.perform(get("/customer/" + 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        new ObjectMapper()
                                .writeValueAsString(
                                new Customer(
                                        "John",
                                        "Doe",
                                        "123 Main St",
                                        "123-45-6789",
                                        new CPI(30)))));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        this.mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        new ObjectMapper()
                                .writeValueAsString(
                                List.of(
                                        new Customer(
                                                "John",
                                                "Doe",
                                                "123 Main St",
                                                "123-45-6789",
                                                new CPI(30)),
                                        new Customer(
                                                "Jane",
                                                "Doe",
                                                "123 Main St",
                                                "123-45-6789",
                                                new CPI(30)),
                                        new Customer(
                                                "Peter",
                                                "Doe",
                                                "123 Main St",
                                                "123-45-6789",
                                                new CPI(30))))));
    }

    @Test
    void addNewCustomer2() throws Exception{
        mockMvc.perform(post("/newCustomer2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper()
                        .writeValueAsString(new Customer(
                                "Test",
                                "Test",
                                "123 Main St",
                                "123-45-6789",
                                new CPI(100)))))
                .andExpect(status().isOk())
                .andExpect(content().string("New customer added: Test Test"));
    }


    @Test
    void addExistingAccount() throws Exception {
        mockMvc.perform(post("/customer/1/addExistingAccount/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account 1 added to John Doe"));
    }

    @Test
    void    addAccountCustomer() throws Exception {
        mockMvc.perform(post("/customer/1/addAccount"))
                .andExpect(status().isOk())
                .andExpect(content().string("New Account added to John Doe"));
    }

    @Test
    public void getByFirstName() throws Exception {
        this.mockMvc.perform(get("/customer/firstName/" + "John"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        new ObjectMapper()
                                .writeValueAsString(List.of(
                                        new Customer(
                                                "John",
                                                "Doe",
                                                "123 Main St",
                                                "123-45-6789",
                                                new CPI(30))))));
    }
}
