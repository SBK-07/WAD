package com.example.shoppingcart.repository;

import com.example.shoppingcart.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}