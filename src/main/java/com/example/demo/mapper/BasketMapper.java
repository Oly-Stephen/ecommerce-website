package com.example.demo.mapper;

import com.example.demo.dto.BasketDTO;
import com.example.demo.dto.BasketItemDTO;
import com.example.demo.model.Basket;
import com.example.demo.model.BasketItem;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    @Mapping(target = "userIds", expression = "java(mapUserIds(basket.getUsers()))")
    @Mapping(target = "items", source = "items")
    BasketDTO toDTO(Basket basket);

    @Mapping(target = "users", ignore = true) // need to handled manually
    @Mapping(target = "items", source = "items")
    Basket toEntity(BasketDTO basketDTO);

    List<BasketItemDTO> toDTO(List<BasketItem> items);

    List<BasketItem> toEntity(List<BasketItemDTO> items);

    default List<Long> mapUserIds(List<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    @Mapping(source = "product.id", target = "productId")
    BasketItemDTO toDTO(BasketItem item);

    @Mapping(source = "productId", target = "product.id")
    BasketItem fromDTO(BasketItemDTO dto);
}