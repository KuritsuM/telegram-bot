package com.kaydash_dmitriy.telegrambot.entity;

import javax.persistence.*;

@Entity
public class ClientOrder {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer status;

    @Column(precision = 2, nullable = false)
    private Double total;

    @ManyToOne
    private Client client;

    public ClientOrder() { }

    public ClientOrder(Integer status, Double total, Client client) {
        this.status = status;
        this.total = total;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
