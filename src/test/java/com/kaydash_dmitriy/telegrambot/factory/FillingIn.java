package com.kaydash_dmitriy.telegrambot.factory;

import com.kaydash_dmitriy.telegrambot.entity.Category;

import com.kaydash_dmitriy.telegrambot.entity.Product;
import com.kaydash_dmitriy.telegrambot.repository.CategoryRepository;
import com.kaydash_dmitriy.telegrambot.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:file:./task-2-test;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE"
})
public class FillingIn {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Category parent;

    @BeforeEach
    public void createParentCategory() {
        Category category = new Category("parent", null);
        categoryRepository.save(category);
        this.parent = categoryRepository.findAll().get(0);
    }

    @Test
    public void createTestCategoriesHierarchy() {
        Category pizzaCategory = new Category("Пицца", parent);
        Category rollsCategory = new Category("Роллы", parent);
        Category burgersCategory = new Category("Бургеры", parent);
        Category drinksCategory = new Category("Напитки", parent);

        categoryRepository.save(pizzaCategory);
        categoryRepository.save(rollsCategory);
        categoryRepository.save(burgersCategory);
        categoryRepository.save(drinksCategory);

        createRollsCategories(rollsCategory);
        createBurgersCategories(burgersCategory);
        createDrinksCategories(drinksCategory);
    }

    @Test()
    public void createProducts() {
        Category gassedDrinks = categoryRepository.findByName("Газированные напитки").get(0);

        createGassedDrinksProducts(gassedDrinks);
    }

    private void createRollsCategories(Category rollsCategory) {
        ArrayList<Category> rolls = new ArrayList<>(Arrays.asList(
                new Category("Классические роллы", rollsCategory),
                new Category("Запеченные роллы", rollsCategory),
                new Category("Сладкие роллы", rollsCategory),
                new Category("Наборы", rollsCategory)
        ));

        categoryRepository.saveAll(rolls);
    }

    private void createBurgersCategories(Category burgersCategory) {
        ArrayList<Category> burgers = new ArrayList<>(Arrays.asList(
                new Category("Классические бургеры", burgersCategory),
                new Category("Острые бургеры", burgersCategory)
        ));

        categoryRepository.saveAll(burgers);
    }

    private void createDrinksCategories(Category drinksCategory) {
        ArrayList<Category> drinks = new ArrayList<>(Arrays.asList(
                new Category("Газированные напитки", drinksCategory),
                new Category("Энергетические напитки", drinksCategory),
                new Category("Соки", drinksCategory),
                new Category("Другие", drinksCategory)
        ));

        categoryRepository.saveAll(drinks);
    }

    private void createGassedDrinksProducts(Category gassedDrinks) {
        ArrayList<Product> products = new ArrayList<>(Arrays.asList(
                new Product("Кока-кола", "Газированные напиток производства CocaColaCo", 65.0, gassedDrinks),
                new Product("Пепси", "Газированные напиток производства PepsiCo", 65.0, gassedDrinks),
                new Product("Черноголовка", "Отечественная кока-кола (конечно!)", 75.0, gassedDrinks)
        ));

        productRepository.saveAll(products);
    }
}
