package com.kaydash_dmitriy.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")
public interface ClientOrderRepository extends JpaRepository<com.kaydash_dmitriy.telegrambot.entity.ClientOrder, Long> {
}
