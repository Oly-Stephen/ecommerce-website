// src/main/java/com/example/demo/service/BasketService.java
package com.example.demo.service;

import com.example.demo.dto.BasketDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.mapper.BasketMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Basket;
import com.example.demo.model.BasketItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repositories.BasketRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.exception.BasketNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BasketMapper basketMapper;
    private final ProductMapper productMapper;

    public BasketService(BasketRepository basketRepository, ProductRepository productRepository,
                         UserRepository userRepository, BasketMapper basketMapper, ProductMapper productMapper) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.basketMapper = basketMapper;
        this.productMapper = productMapper;
    }

    @Transactional
    public BasketDTO addToBasket(Long productId, Integer quantity) {
        Basket basket = new Basket();
        Product product = getProduct(productId);
        BasketItem item = new BasketItem();
        item.setBasket(basket);
        item.setProduct(product);
        item.setQuantity(quantity);
        basket.getItems().add(item);
        basketRepository.save(basket);
        return basketMapper.toDTO(basket);
    }

    @Transactional
    public BasketDTO updateBasket(Long basketId, Long productId, Integer quantity) {
        Basket basket = getBasket(basketId);
        Product product = getProduct(productId);

        BasketItem item = basket.getItems().stream()
            .filter(basketItem -> basketItem.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);

        if (item == null) {
            if (quantity > 0) {
                item = new BasketItem();
                item.setBasket(basket);
                item.setProduct(product);
                item.setQuantity(quantity);
                basket.getItems().add(item);
            }
        } else {
            int newQuantity = item.getQuantity() + quantity;
            if (newQuantity > 0) {
                item.setQuantity(newQuantity);
            } else {
                basket.getItems().remove(item);
            }
        }

        basketRepository.save(basket);
        return basketMapper.toDTO(basket);
    }

    public List<ProductDTO> getProductsInBasketById(Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found")); // Handle this exception properly

        return basket.getItems().stream()
                .map(BasketItem::getProduct)
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BasketDTO shareBasket(Long basketId, Long userId) {
        Basket basket = getBasket(basketId);
        User user = getUser(userId);
        basket.getUsers().add(user);
        basketRepository.save(basket);
        return basketMapper.toDTO(basket);
    }

    public List<ProductDTO> getSharedBasketProducts(Long basketId, Long userId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found")); // Handle this exception properly

        if (!basket.getUsers().stream().anyMatch(user -> user.getId().equals(userId))) {
            throw new RuntimeException("User does not have access to this basket"); // Handle this exception properly
        }

        return basket.getItems().stream()
                .map(BasketItem::getProduct)
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BasketDTO updateBasketBySharedUser(Long basketId, Long userId, Long productId, Integer quantity) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found")); // Handle this exception properly

        if (!basket.getUsers().stream().anyMatch(user -> user.getId().equals(userId))) {
            throw new RuntimeException("User does not have access to this basket"); // Handle this exception properly
        }

        Product product = getProduct(productId);

        BasketItem item = basket.getItems().stream()
            .filter(basketItem -> basketItem.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);

        if (item == null) {
            if (quantity > 0) {
                item = new BasketItem();
                item.setBasket(basket);
                item.setProduct(product);
                item.setQuantity(quantity);
                basket.getItems().add(item);
            }
        } else {
            int newQuantity = item.getQuantity() + quantity;
            if (newQuantity > 0) {
                item.setQuantity(newQuantity);
            } else {
                basket.getItems().remove(item);
            }
        }

        basketRepository.save(basket);
        return basketMapper.toDTO(basket);
    }

    @Transactional
    public BasketDTO addProductBySharedUser(Long basketId, Long userId, Long productId, Integer quantity) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found")); // Handle this exception properly

        if (!basket.getUsers().stream().anyMatch(user -> user.getId().equals(userId))) {
            throw new RuntimeException("User does not have access to this basket"); // Handle this exception properly
        }

        Product product = getProduct(productId);

        BasketItem item = new BasketItem();
        item.setBasket(basket);
        item.setProduct(product);
        item.setQuantity(quantity);
        basket.getItems().add(item);

        basketRepository.save(basket);
        return basketMapper.toDTO(basket);
    }

    private Basket getBasket(Long id) {
        return basketRepository.findById(id).orElseThrow(() -> new BasketNotFoundException("Basket with id " + id + " not found"));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with id " + id + " not found"));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }
}