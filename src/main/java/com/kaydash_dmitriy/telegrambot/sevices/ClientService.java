package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.Client;

public interface ClientService {
    Client getClientByName(String name);

    Client createClient(Client client);

    Client updateClient(Client client);

    Client getClientByExternalId(Long externalId);
}
