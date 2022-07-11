package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import com.kaydash_dmitriy.telegrambot.repository.ClientOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOrderServiceImpl implements ClientOrderService{
    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Override
    public List<ClientOrder> getOrdersByStatus(Integer status) {
        return clientOrderRepository.getClientOrdersByStatus(status);
    }

    @Override
    public List<ClientOrder> getOrderByClientName(String name) {
        return clientOrderRepository.getClientOrdersByClient_FullName(name);
    }
}
