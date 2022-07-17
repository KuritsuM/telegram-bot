package com.kaydash_dmitriy.telegrambot.sevices.Impl;

import com.kaydash_dmitriy.telegrambot.entity.ClientOrder;
import com.kaydash_dmitriy.telegrambot.entity.OrderProduct;
import com.kaydash_dmitriy.telegrambot.entity.Product;
import com.kaydash_dmitriy.telegrambot.repository.ClientOrderRepository;
import com.kaydash_dmitriy.telegrambot.repository.OrderProductRepository;
import com.kaydash_dmitriy.telegrambot.sevices.ClientOrderService;
import com.kaydash_dmitriy.telegrambot.sevices.ClientService;
import com.kaydash_dmitriy.telegrambot.sevices.OrderProductService;
import com.kaydash_dmitriy.telegrambot.sevices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public List<OrderProduct> getOrderByClientOrder(ClientOrder clientOrder) {
        return orderProductRepository.getOrderProductsByClientOrder(clientOrder);
    }

    @Override
    public OrderProduct addToCart(Long externalId, String productName) {
        ClientOrder clientOrder = addOrCreateOrder(externalId);
        return addProductToClientOrder(clientOrder, productName);
    }

    private ClientOrder addOrCreateOrder(Long externalId) {
        List<ClientOrder> clientOrders = clientOrderService.getOrdersByClientExternalId(externalId).stream()
                .filter(clientOrder -> clientOrder.getStatus().equals(ClientOrderService.ORDER_OPEN))
                .collect(Collectors.toList());

        if (clientOrders.size() > 0) {
            return clientOrders.get(0);
        }
        else {
            return createOrder(externalId);
        }
    }

    private OrderProduct addProductToClientOrder(ClientOrder clientOrder, String productName) {
        List<OrderProduct> orderProducts = getOrderByClientOrder(clientOrder);

        List<OrderProduct> filteredByProductName = orderProducts.stream()
                .filter(orderProduct -> orderProduct.getProduct().getName().equals(productName))
                .collect(Collectors.toList());

        if (filteredByProductName.size() > 0) {
            OrderProduct orderProduct = filteredByProductName.get(0);
            orderProduct.setCountProduct(orderProduct.getCountProduct() + 1L);
            clientOrder.setTotal(clientOrder.getTotal() + orderProduct.getProduct().getPrice());

            clientOrderService.createClientOrder(clientOrder);
            return orderProductRepository.save(orderProduct);
        }
        else {
            Product product = productService.getProductByName(productName);

            clientOrder.setTotal(clientOrder.getTotal() + product.getPrice());

            clientOrderService.createClientOrder(clientOrder);

            return orderProductRepository.save(new OrderProduct(
                    1L,
                    clientOrder,
                    product
            ));
        }
    }

    private ClientOrder createOrder(Long externalId) {
        return clientOrderService.createClientOrder(new ClientOrder(
                ClientOrderService.ORDER_OPEN,
                0.0,
                clientService.getClientByExternalId(externalId)
        ));
    }
}
