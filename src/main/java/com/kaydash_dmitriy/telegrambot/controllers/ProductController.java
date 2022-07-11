package com.kaydash_dmitriy.telegrambot.controllers;

import com.kaydash_dmitriy.telegrambot.entity.Product;
import com.kaydash_dmitriy.telegrambot.sevices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/rest/products", params = { "name" })
    public Product getProductByName(String name) {
        return productService.getProductByName(name);
    }

    @GetMapping(value = "/rest/products", params = { "categoryId" })
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping(value = "/rest/listClientProducts", params = { "clientId" })
    public List<Product> getProductsByClientId(Long clientId) {
        return productService.getClientOrderProductsByClientId(clientId);
    }

    @GetMapping(value = "/rest/topPopularProducts", params = { "top" })
    public List<Product> getProductsByPopularity(int top) {
        return productService.getProductsTopLimitBy(top);
    }
}
