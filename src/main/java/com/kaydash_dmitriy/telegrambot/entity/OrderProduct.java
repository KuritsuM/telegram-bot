package com.kaydash_dmitriy.telegrambot.entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer countProducts;

    @ManyToOne
    private ClientOrder clientOrder;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(Integer countProducts) {
        this.countProducts = countProducts;
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
