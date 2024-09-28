package com.example.demo.dto;

import lombok.Data;

@Data
public class BasketItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
}
