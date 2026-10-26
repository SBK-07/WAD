package com.example.shoppingcart.controller;

import com.example.shoppingcart.dto.AddToCartRequest;
import com.example.shoppingcart.dto.UpdateCartRequest;
import com.example.shoppingcart.model.CartItem;
import com.example.shoppingcart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart() {
        return cartService.getCart();
    }

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(request);
    }

    @PutMapping("/update")
    public CartItem updateQuantity(
            @RequestBody UpdateCartRequest request) {
        return cartService.updateQuantity(request);
    }

    @DeleteMapping("/remove/{productId}")
    public void removeFromCart(@PathVariable String productId) {
        cartService.removeFromCart(productId);
    }

    @GetMapping("/total")
    public Map<String, Double> getTotalAmount() {
        return Map.of(
                "total", cartService.getTotalAmount()
        );
    }
}