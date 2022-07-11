package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;

import java.util.List;

public interface ClientOrderService {
    List<ClientOrder> getOrdersByStatus(Integer status);

    List<ClientOrder> getOrderByClientName(String name);
}
