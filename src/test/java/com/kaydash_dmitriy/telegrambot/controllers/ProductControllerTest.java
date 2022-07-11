package com.kaydash_dmitriy.telegrambot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetProductsByNameAndReturnOk() throws Exception {
        this.mockMvc.perform(get("/rest/products?name=example_product")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetProductsByCategoryIdAndReturnOk() throws Exception {
        this.mockMvc.perform(get("/rest/products?categoryId=42")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetProductsByClientIdAndReturnOk() throws Exception {
        this.mockMvc.perform(get("/rest/listClientProducts?clientId=42")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetTopPopularProductAndReturnOk() throws Exception {
        this.mockMvc.perform(get("/rest/topPopularProducts?top=42")).andExpect(status().isOk());
    }
}
