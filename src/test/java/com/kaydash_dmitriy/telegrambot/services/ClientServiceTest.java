package com.kaydash_dmitriy.telegrambot.services;

import com.kaydash_dmitriy.telegrambot.entity.Client;
import com.kaydash_dmitriy.telegrambot.repository.ClientRepository;
import com.kaydash_dmitriy.telegrambot.sevices.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = {
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.datasource.url=jdbc:h2:file:./task-3-test;;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE"
        }
)
public class ClientServiceTest {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    public void getClientByName() {
        String clientNameThatWeLookingFor = "clientNameThatWeLookingFor";
        createClients(clientNameThatWeLookingFor);

        assertEquals(clientService.getClientByName(clientNameThatWeLookingFor).getFullName(), clientNameThatWeLookingFor);
    }

    private void createClients(String clientName) {
        Client client = new Client(clientName, "42", "aaa", 42L);
        Client anotherClient = new Client(clientName + "42", "4242", "aaa42", 55L);

        clientRepository.save(client);
        clientRepository.save(anotherClient);
    }
}
