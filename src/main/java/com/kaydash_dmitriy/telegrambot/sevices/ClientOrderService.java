package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;

import java.util.List;

public interface ClientOrderService {
    Integer ORDER_OPEN = 1;
    Integer ORDER_CLOSED = 2;

    List<ClientOrder> getOrdersByStatus(Integer status);

    List<ClientOrder> getOrderByClientName(String name);

    List<ClientOrder> getOrdersByClientExternalId(Long externalId);

    ClientOrder getOpenOrderByClientExternalId(Long externalId);

    ClientOrder closeClientOrderByExternalId(Long externalId);

    ClientOrder createClientOrder(ClientOrder clientOrder);
}
