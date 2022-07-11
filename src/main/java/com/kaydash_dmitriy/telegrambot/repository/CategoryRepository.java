package com.kaydash_dmitriy.telegrambot.repository;

import com.kaydash_dmitriy.telegrambot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNotNull();
}
