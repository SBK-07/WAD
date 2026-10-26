package com.example.shoppingcart.service;

import com.example.shoppingcart.dto.AddToCartRequest;
import com.example.shoppingcart.dto.UpdateCartRequest;
import com.example.shoppingcart.model.CartItem;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.CartRepository;
import com.example.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<CartItem> getCart() {
        return cartRepository.findAll();
    }

    public CartItem addToCart(AddToCartRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cartRepository
                .findByProductId(request.getProductId())
                .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity() + request.getQuantity()
            );

            return cartRepository.save(existingItem);
        }

        CartItem cartItem = new CartItem();

        cartItem.setProductId(product.getId());
        cartItem.setName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(request.getQuantity());

        return cartRepository.save(cartItem);
    }

    public CartItem updateQuantity(UpdateCartRequest request) {

        CartItem cartItem = cartRepository
                .findByProductId(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (request.getQuantity() < 1) {
            throw new RuntimeException("Quantity must be at least 1");
        }

        cartItem.setQuantity(request.getQuantity());

        return cartRepository.save(cartItem);
    }

    public void removeFromCart(String productId) {

        CartItem cartItem = cartRepository
                .findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartRepository.delete(cartItem);
    }

    public double getTotalAmount() {

        return cartRepository.findAll()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}