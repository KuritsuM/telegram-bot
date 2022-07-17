package com.kaydash_dmitriy.telegrambot.repository;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import com.kaydash_dmitriy.telegrambot.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> getOrderProductsByClientOrder(ClientOrder clientOrder);
}
