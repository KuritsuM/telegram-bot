package com.kaydash_dmitriy.telegrambot.factory;

import com.kaydash_dmitriy.telegrambot.entity.Category;

import com.kaydash_dmitriy.telegrambot.entity.Product;
import com.kaydash_dmitriy.telegrambot.repository.CategoryRepository;
import com.kaydash_dmitriy.telegrambot.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:h2:file:~/task-3;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE"
})
public class FillingIn {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void createTestCategoriesHierarchy() {
        Category parent = categoryRepository.save(new Category("parent", null));

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
        List<Category> drinks = createDrinksCategories(drinksCategory);

        Category someCategory = drinks.get(0);

        createGassedDrinksProducts(someCategory);
    }

    private List<Category> createRollsCategories(Category rollsCategory) {
        List<Category> rolls = List.of(
                new Category("Классические роллы", rollsCategory),
                new Category("Запеченные роллы", rollsCategory),
                new Category("Сладкие роллы", rollsCategory),
                new Category("Наборы", rollsCategory)
        );

        categoryRepository.saveAll(rolls);

        return rolls;
    }

    private List<Category> createBurgersCategories(Category burgersCategory) {
        List<Category> burgers = List.of(
                new Category("Классические бургеры", burgersCategory),
                new Category("Острые бургеры", burgersCategory)
        );

        categoryRepository.saveAll(burgers);

        return burgers;
    }

    private List<Category> createDrinksCategories(Category drinksCategory) {
        List<Category> drinks = List.of(
                new Category("Газированные напитки", drinksCategory),
                new Category("Энергетические напитки", drinksCategory),
                new Category("Соки", drinksCategory),
                new Category("Другие", drinksCategory)
        );

        categoryRepository.saveAll(drinks);

        return drinks;
    }

    private List<Product> createGassedDrinksProducts(Category someCategory) {
        List<Product> products = List.of(
                new Product("Example1", "Газированные напиток производства CocaColaCo", 65.0, someCategory),
                new Product("Example2", "Газированные напиток производства PepsiCo", 65.0, someCategory),
                new Product("Example3", "Отечественная кока-кола (конечно!)", 75.0, someCategory)
        );

        productRepository.saveAll(products);

        return products;
    }
}
