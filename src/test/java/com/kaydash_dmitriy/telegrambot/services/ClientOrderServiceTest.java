package com.kaydash_dmitriy.telegrambot.services;

import com.kaydash_dmitriy.telegrambot.entity.Client;
import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import com.kaydash_dmitriy.telegrambot.repository.ClientOrderRepository;
import com.kaydash_dmitriy.telegrambot.repository.ClientRepository;
import com.kaydash_dmitriy.telegrambot.sevices.ClientOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest(
        properties = {
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.datasource.url=jdbc:h2:file:./task-3-test;;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE"
        }
)
public class ClientOrderServiceTest {
    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void getOrderByStatus() {
        List<ClientOrder> foundThidOrders = List.of(
                new ClientOrder(42, 100.52, null),
                new ClientOrder(42, 110.11, null),
                new ClientOrder(42, 2566.13, null)
        );

        List<ClientOrder> notForFound = List.of(
                new ClientOrder(1, 42.42, null),
                new ClientOrder(1, 3.14, null)
        );

        clientOrderRepository.saveAll(foundThidOrders);
        clientOrderRepository.saveAll(notForFound);

        assertEquals(clientOrderService.getOrdersByStatus(42).size(), foundThidOrders.size());
    }

    @Test
    public void getOrderByClientName() {
        String clientNameThatWeLookingFor = "clientNameThatWeLookingFor";
        Client client = new Client(clientNameThatWeLookingFor, "79787878841", "asd", 42L);
        Client anotherClient = new Client("cl", "42", "sevsu", 65536L);

        clientRepository.save(client);
        clientRepository.save(anotherClient);

        List<ClientOrder> foundThidOrders = List.of(
                new ClientOrder(42, 100.52, client),
                new ClientOrder(42, 110.11, client),
                new ClientOrder(42, 2566.13, client)
        );

        List<ClientOrder> notForFound = List.of(
                new ClientOrder(1, 42.42, anotherClient),
                new ClientOrder(1, 3.14, anotherClient)
        );

        clientOrderRepository.saveAll(foundThidOrders);
        clientOrderRepository.saveAll(notForFound);

        assertEquals(clientOrderService.getOrderByClientName(clientNameThatWeLookingFor).size(), foundThidOrders.size());
    }
}
