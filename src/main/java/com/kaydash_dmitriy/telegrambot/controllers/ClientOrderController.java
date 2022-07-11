package com.kaydash_dmitriy.telegrambot.controllers;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import com.kaydash_dmitriy.telegrambot.sevices.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientOrderController {

    @Autowired
    private ClientOrderService clientOrderService;

    @GetMapping(value = "/rest/orders", params = { "status" })
    public List<ClientOrder> getAllOrdersByStatus(int status) {
        return clientOrderService.getOrdersByStatus(status);
    }

    @GetMapping(value = "/rest/listClientOrders", params = { "clientName" })
    public List<ClientOrder> getOrdersByClientName(String clientName) {
        return clientOrderService.getOrderByClientName(clientName);
    }
}
