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
public class ClientOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetListClientOrdersAndReturnOk() throws Exception {
        this.mockMvc.perform(get("/rest/listClientOrders?clientName=example_name")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetOrdersAndReturnOk() throws Exception {
        this.mockMvc.perform(get("/rest/orders?status=42")).andExpect(status().isOk());
    }
}
