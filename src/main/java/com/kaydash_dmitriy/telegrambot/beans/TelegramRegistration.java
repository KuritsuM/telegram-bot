package com.kaydash_dmitriy.telegrambot.beans;

import com.kaydash_dmitriy.telegrambot.entity.Client;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

public class TelegramRegistration {
    @Value("${telegram.token}")
    private HashMap<Long, Client> clientRegistrationsSession;

    @Bean(name = "telegramRegistrationSession")
    public HashMap<Long, Client> clientRegistration() {
        if (clientRegistrationsSession == null) {
            clientRegistrationsSession = new HashMap<>();
        }

        return clientRegistrationsSession;
    }
}
