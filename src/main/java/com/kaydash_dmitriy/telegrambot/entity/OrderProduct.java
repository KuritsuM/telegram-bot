package com.kaydash_dmitriy.telegrambot.entity;

import javax.persistence.*;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long countProduct;

    @ManyToOne
    private ClientOrder clientOrder;

    @ManyToOne
    private Product product;

    public OrderProduct() { }

    public OrderProduct(Long countProduct, ClientOrder clientOrder, Product product) {
        this.countProduct = countProduct;
        this.clientOrder = clientOrder;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(Long countProducts) {
        this.countProduct = countProducts;
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
