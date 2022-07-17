package com.kaydash_dmitriy.telegrambot.repository;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    List<ClientOrder> getClientOrdersByStatus(Integer status);

    List<ClientOrder> getClientOrdersByClientFullName(String fullName);

    List<ClientOrder> getClientOrdersByClientExternalId(Long externalId);

    ClientOrder getClientOrderByClientExternalIdAndStatus(Long externalId, Integer status);
}
