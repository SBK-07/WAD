package com.example.shoppingcart.repository;

import com.example.shoppingcart.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<CartItem, String> {

    Optional<CartItem> findByProductId(String productId);

    void deleteByProductId(String productId);
}