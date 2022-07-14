package com.kaydash_dmitriy.telegrambot.sevices.Impl;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import com.kaydash_dmitriy.telegrambot.repository.ClientOrderRepository;
import com.kaydash_dmitriy.telegrambot.sevices.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOrderServiceImpl implements ClientOrderService {
    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Override
    public List<ClientOrder> getOrdersByStatus(Integer status) {
        return clientOrderRepository.getClientOrdersByStatus(status);
    }

    @Override
    public List<ClientOrder> getOrderByClientName(String name) {
        return clientOrderRepository.getClientOrdersByClientFullName(name);
    }

    @Override
    public List<ClientOrder> getOrdersByClientExternalId(Long externalId) {
        return clientOrderRepository.getClientOrdersByClientExternalId(externalId);
    }

    @Override
    public ClientOrder createClientOrder(ClientOrder clientOrder) {
        return clientOrderRepository.save(clientOrder);
    }

    @Override
    public ClientOrder getOpenOrderByClientExternalId(Long externalId) {
        return clientOrderRepository.getClientOrderByClientExternalIdAndStatus(externalId, ClientOrderService.ORDER_OPEN);
    }

    @Override
    public ClientOrder closeClientOrderByExternalId(Long externalId) {
        ClientOrder clientOrder = getOpenOrderByClientExternalId(externalId);
        clientOrder.setStatus(ClientOrderService.ORDER_CLOSED);
        return clientOrderRepository.save(clientOrder);
    }
}
