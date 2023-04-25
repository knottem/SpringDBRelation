package com.example.springdbrelation.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloWorldController.class)
public class HelloWorldControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
       this.mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello World!"));
    }
}
