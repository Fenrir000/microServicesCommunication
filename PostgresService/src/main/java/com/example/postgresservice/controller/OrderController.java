package com.example.postgresservice.controller;

import com.example.postgresservice.dto.OrderRequest;
import com.example.postgresservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String makeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.makeOrder(orderRequest);
        return "Order made!";
    }

}
