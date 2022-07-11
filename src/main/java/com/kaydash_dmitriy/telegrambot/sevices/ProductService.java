package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductByName(String name);

    List<Product> getProductsByCategoryId(Long id);

    List<Product> getClientOrderProductsByClientId(Long clientId);

    List<Product> getProductsTopLimitBy(int topLimit);
}
