package com.kaydash_dmitriy.telegrambot.controllers;

import com.kaydash_dmitriy.telegrambot.entity.Client;
import com.kaydash_dmitriy.telegrambot.sevices.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/rest/clients", params = { "name" })
    public Client getClientByName(String name) {
        return clientService.getClientByName(name);
    }
}
