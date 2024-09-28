package com.example.demo.dto;

import com.example.demo.model.BasketItem;
import lombok.Data;

import java.util.List;

@Data
public class BasketDTO {
    private Long id;
    private List<Long> userIds;
    private List<BasketItemDTO> items;

}