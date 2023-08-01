package com.example.postgresservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {
    private Long id;
    private String uniqueCode;
    private BigDecimal price;
    private Integer quantity;
}
