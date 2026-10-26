package com.example.shoppingcart.service;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // CREATE
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // READ - Get all products
    public List<Product> getAllProducts () {
        return productRepository.findAll();
    }

    // READ - Get product by ID
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // UPDATE
    public Optional<Product> updateProduct(String id, Product updatedProduct) {

        return productRepository.findById(id)
                .map(existingProduct -> {

                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setPrice(updatedProduct.getPrice());

                    return productRepository.save(existingProduct);
                });
    }

    // DELETE
    public boolean deleteProduct(String id) {

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
