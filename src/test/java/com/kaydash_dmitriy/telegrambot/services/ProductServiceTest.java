package com.kaydash_dmitriy.telegrambot.services;

import com.kaydash_dmitriy.telegrambot.entity.*;
import com.kaydash_dmitriy.telegrambot.repository.*;
import com.kaydash_dmitriy.telegrambot.sevices.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = {
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.datasource.url=jdbc:h2:file:./task-3-test;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE"
        }
)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ClientOrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void getProductByName() {
        String productName = "productName";
        createProducts(productName);

        assertEquals(productService.getProductByName(productName).getName(), productName);
    }

    @Test
    public void getProductsByCategoryId() {
        List<Product> products = createProductsWithCategory();
        Long categoryId = products.get(0).getCategory().getId();

        assertEquals(productService.getProductsByCategoryId(categoryId).size(), products.size());
    }

    @Test
    public void getProductsByClientName() {
        String clientName = "clientName";
        Client client = createClient(clientName);
        List<Product> products = createProducts("someName");

        createOrderProducts(createClientOrder(client), products);
        createOrderProducts(createClientOrder(client), List.of(products.get(products.size() - 1)));

        assertEquals(productService.getClientOrderProductsByClientId(client.getId()).size(), products.size());
    }

    @Test
    public void getProductsTopByLimit() {
        List<Product> products = createProducts("someProductName");
        createOrderProduct(10000L, products.get(products.size() - 1));
        createOrderProduct(4200L, products.get(0));
        List<Product> productsTop = List.of(
                products.get(products.size() - 1),
                products.get(0)
        );

        assertEquals(productService.getProductsTopLimitBy(2), productsTop);
    }

    private List<Product> createProductsWithCategory() {
        Category category = createCategories().get(0);
        return createProducts("doesn't matter", category);
    }

    private List<Category> createCategories() {
        List<Category> categories = List.of(
                new Category("categoryWeLookingFor", null),
                new Category("anotherCategory", null)
        );

        categoryRepository.saveAll(categories);

        return categories;
    }

    private List<OrderProduct> createOrderProducts(ClientOrder clientOrder, List<Product> products) {
        List<OrderProduct> orderProducts = new ArrayList<>();

        products.forEach(product -> { orderProducts.add(new OrderProduct(10L, clientOrder, product)); });

        return orderProductRepository.saveAll(orderProducts);
    }

    private OrderProduct createOrderProduct(Long productCount, Product product) {
        return orderProductRepository.save(new OrderProduct(productCount, null, product));
    }

    private Client createClient(String clientName) {
        Client client = new Client(clientName, "42", "aaa", 42L);

        return clientRepository.save(client);
    }

    private ClientOrder createClientOrder(Client client) {
        ClientOrder clientOrder = new ClientOrder(42, 100.15, client);

        return orderRepository.save(clientOrder);
    }

    private List<Product> createProducts(String productName) {
        List<Product> products = List.of(
                new Product(productName, "descr42", 100.0, null),
                new Product(productName + "42", "descr42", 100.0, null)
        );

        return productRepository.saveAll(products);
    }

    private List<Product> createProducts(String productName, Category category) {
        List<Product> products = List.of(
                new Product(productName, "descr42", 100.0, category),
                new Product(productName + "42", "descr42", 100.0, category),
                new Product(productName + "314", "descr42", 100.0, category)
        );

        return productRepository.saveAll(products);
    }
}
