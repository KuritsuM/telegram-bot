package com.kaydash_dmitriy.telegrambot.sevices.Impl;

import com.kaydash_dmitriy.telegrambot.entity.Category;
import com.kaydash_dmitriy.telegrambot.repository.CategoryRepository;
import com.kaydash_dmitriy.telegrambot.sevices.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoryByParentId(Long parentId) {
        return categoryRepository.getCategoryByParentId(parentId);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getCategoryByName(categoryName);
    }
}
