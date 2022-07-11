package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.Client;

public interface ClientService {
    Client getClientByName(String name);
}
