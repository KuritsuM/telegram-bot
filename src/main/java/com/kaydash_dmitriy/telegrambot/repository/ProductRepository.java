package com.kaydash_dmitriy.telegrambot.repository;

import com.kaydash_dmitriy.telegrambot.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    String TOP_POPULARITY_QUERY = "SELECT p.product FROM OrderProduct p GROUP BY " +
            "p.product.id ORDER BY SUM(p.countProduct) DESC";

    String ORDER_PRODUCTS_QUERY = "SELECT DISTINCT op.product FROM OrderProduct op WHERE op.clientOrder.client.id " +
            "= :clientId";

    Product getProductByName(String name);

    List<Product> getProductsByCategoryId(Long id);

    @Query(TOP_POPULARITY_QUERY)
    List<Product> getProductsTopLimitBy(Pageable pageable);

    @Query(ORDER_PRODUCTS_QUERY)
    List<Product> getOrderProductsByClientId(@Param("clientId") Long clientId);
}
