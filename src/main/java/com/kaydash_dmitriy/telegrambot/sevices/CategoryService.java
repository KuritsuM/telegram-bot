package com.kaydash_dmitriy.telegrambot.sevices;

import com.kaydash_dmitriy.telegrambot.entity.Category;

import java.util.List;

public interface CategoryService {
    static Long PARENT_CATEGORY_ID = 0L;

    List<Category> getCategoryByParentId(Long parentId);

    Category getCategoryByName(String categoryName);
}
