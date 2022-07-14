package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import com.kaydash_dmitriy.telegrambot.entity.OrderProduct;
import com.kaydash_dmitriy.telegrambot.entity.Product;

import java.util.List;

public interface OrderProductService {
    OrderProduct addToCart(Long externalId, String productName);

    List<OrderProduct> getOrderByClientOrder(ClientOrder clientOrder);
}
