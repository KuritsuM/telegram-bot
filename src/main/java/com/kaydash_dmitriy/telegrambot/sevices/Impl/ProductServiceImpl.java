package com.kaydash_dmitriy.telegrambot.sevices.Impl;

import com.kaydash_dmitriy.telegrambot.entity.Product;
import com.kaydash_dmitriy.telegrambot.repository.ProductRepository;
import com.kaydash_dmitriy.telegrambot.sevices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductByName(String name) {
        return productRepository.getProductByName(name);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return productRepository.getProductsByCategoryId(id);
    }

    @Override
    public List<Product> getClientOrderProductsByClientId(Long clientId) {
        return productRepository.getOrderProductsByClientId(clientId);
    }

    @Override
    public List<Product> getProductsTopLimitBy(int topLimit) {
        return productRepository.getProductsTopLimitBy(Pageable.ofSize(topLimit));
    }
}
