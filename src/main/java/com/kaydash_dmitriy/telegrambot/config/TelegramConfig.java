package com.kaydash_dmitriy.telegrambot.config;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfig {
    @Value("${telegram.token}")
    private String token;

    @Bean(name = "telegramBot")
    public TelegramBot createTelegramBot() {
        return new TelegramBot(token);
    }
}
