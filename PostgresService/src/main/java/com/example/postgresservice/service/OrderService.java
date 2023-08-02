package com.example.postgresservice.service;

import com.example.postgresservice.dto.InventoryResponse;
import com.example.postgresservice.dto.OrderItemsDto;
import com.example.postgresservice.dto.OrderRequest;
import com.example.postgresservice.model.Order;
import com.example.postgresservice.model.OrderItem;
import com.example.postgresservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;
    private WebClient.Builder webClientBuilder;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void makeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItemsList = orderRequest.
                getOrderItemsDtos().
                stream().
                map(this::mapToDto).
                toList();
        order.setOrderItems(orderItemsList);
        List<String> uniqueCodes = order.getOrderItems().stream().map(OrderItem::getUniqueCode).toList();
        InventoryResponse[] res = webClientBuilder.build().get().uri("http://inventoryService/api/inventory",uriBuilder -> uriBuilder.queryParam("uniqueCode",uniqueCodes).
                build()).
                retrieve().
                bodyToMono(InventoryResponse[].class).
                block();

        boolean allProductsIsInInventory = Arrays.stream(res).allMatch(InventoryResponse::isInInventory);
        if (allProductsIsInInventory) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Not in inventory");
        }


    }

    private OrderItem mapToDto(OrderItemsDto orderItemsDto) {
        OrderItem orderItems = new OrderItem();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        orderItems.setUniqueCode(orderItemsDto.getUniqueCode());
        return orderItems;


    }
}
