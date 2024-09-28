package com.example.demo.controller;

import com.example.demo.dto.BasketDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.BasketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/add")
    public BasketDTO addToBasket(@RequestParam Long productId,
                                 @RequestParam Integer quantity) {
        return basketService.addToBasket(productId, quantity);
    }

    @PutMapping("/{basketId}/update")
    public BasketDTO updateBasket(@PathVariable Long basketId,
                                  @RequestParam Long productId,
                                  @RequestParam Integer quantity) {
        return basketService.updateBasket(basketId, productId, quantity);
    }

    @GetMapping("/{basketId}/products")
    public List<ProductDTO> getProductsInBasketById(@PathVariable Long basketId) {
        return basketService.getProductsInBasketById(basketId);
    }

    @PostMapping("/{basketId}/share")
    public BasketDTO shareBasket(@PathVariable Long basketId, @RequestParam Long userId) {
        return basketService.shareBasket(basketId, userId);
    }

    @GetMapping("/{basketId}/shared/{userId}/products")
    public List<ProductDTO> getSharedBasketProducts(@PathVariable Long basketId, @PathVariable Long userId) {
        return basketService.getSharedBasketProducts(basketId, userId);
    }

    @PutMapping("/{basketId}/shared/{userId}/update")
    public BasketDTO updateBasketBySharedUser(@PathVariable Long basketId, @PathVariable Long userId,
                                              @RequestParam Long productId, @RequestParam Integer quantity) {
        return basketService.updateBasketBySharedUser(basketId, userId, productId, quantity);
    }

    @PostMapping("/{basketId}/shared/{userId}/add")
    public BasketDTO addProductBySharedUser(@PathVariable Long basketId, @PathVariable Long userId,
                                            @RequestParam Long productId, @RequestParam Integer quantity) {
        return basketService.addProductBySharedUser(basketId, userId, productId, quantity);
    }
}