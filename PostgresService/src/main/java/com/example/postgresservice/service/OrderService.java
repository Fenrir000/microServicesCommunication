package com.example.postgresservice.service;

import com.example.postgresservice.dto.OrderItemsDto;
import com.example.postgresservice.dto.OrderRequest;
import com.example.postgresservice.model.Order;
import com.example.postgresservice.model.OrderItems;
import com.example.postgresservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service

public class OrderService {

    private OrderRepository orderRepository;
@Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void makeOrder(OrderRequest orderRequest){
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItemsList =orderRequest.
                getOrderItemsDtos().
                stream().
                map(this::mapToDto).
                toList();
        order.setOrderItems(orderItemsList);
        orderRepository.save(order);
    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
        OrderItems orderItems=new OrderItems();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        orderItems.setUniqueCode(orderItemsDto.getUniqueCode());
        return orderItems;


    }
}
