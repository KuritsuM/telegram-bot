package com.kaydash_dmitriy.telegrambot.sevices.Impl;

import com.kaydash_dmitriy.telegrambot.entity.Client;
import com.kaydash_dmitriy.telegrambot.repository.ClientRepository;
import com.kaydash_dmitriy.telegrambot.sevices.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client getClientByName(String name) {
        return clientRepository.getClientByFullName(name);
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        Client client1 = clientRepository.getById(client.getId());

        if (client1.getFullName() == null) {
            client1.setFullName(client.getFullName());
        }

        if (client1.getAddress() == null) {
            client1.setAddress(client.getAddress());
        }

        if (client1.getPhoneNumber() == null) {
            client1.setPhoneNumber(client.getPhoneNumber());
        }

        return clientRepository.save(client1);
    }

    @Override
    public Client getClientByExternalId(Long externalId) {
        return clientRepository.getClientByExternalId(externalId);
    }
}
