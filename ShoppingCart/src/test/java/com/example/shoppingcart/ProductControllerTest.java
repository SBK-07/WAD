package com.example.shoppingcart;

import com.example.shoppingcart.model.Product;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    private final String testProductId = "TEST-PRODUCT-001";

    @AfterEach
    void cleanup() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", testProductId));
    }

    // ---------------------------------------------------------
    // TC01 - Successful addition of a product
    // ---------------------------------------------------------
    @Test
    void shouldAddProductSuccessfully() throws Exception {

        Product product = new Product(
                testProductId,
                "Test Laptop",
                "Laptop created for testing",
                50000
        );

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(testProductId)))
                .andExpect(jsonPath("$.name", is("Test Laptop")))
                .andExpect(jsonPath("$.description", is("Laptop created for testing")))
                .andExpect(jsonPath("$.price", is(50000.0)));
    }

    // ---------------------------------------------------------
    // TC02 - Addition of a product with missing/invalid data
    // ---------------------------------------------------------
    @Test
    void shouldRejectProductWithInvalidData() throws Exception {

        String invalidProduct = """
                {
                    "id": "TEST-PRODUCT-001",
                    "name": "Test Laptop",
                    "description": "Invalid product",
                    "price": 50000
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProduct))
                .andExpect(status().isBadRequest());
    }

    // ---------------------------------------------------------
    // TC03 - Retrieval of all products
    // ---------------------------------------------------------
    @Test
    void shouldRetrieveAllProducts() throws Exception {

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ---------------------------------------------------------
    // TC04a - Retrieval of a product using a valid ID
    // ---------------------------------------------------------
    @Test
    void shouldRetrieveProductUsingValidId() throws Exception {

        Product product = new Product(
                testProductId,
                "Test Laptop",
                "Laptop created for testing",
                50000
        );

        // Add test product
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(product)))
                .andExpect(status().isCreated());

        // Retrieve using valid ID
        mockMvc.perform(get("/api/products/{id}", testProductId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testProductId)))
                .andExpect(jsonPath("$.name", is("Test Laptop")))
                .andExpect(jsonPath("$.description", is("Laptop created for testing")))
                .andExpect(jsonPath("$.price", is(50000.0)));
    }

    // ---------------------------------------------------------
    // TC04b - Retrieval of a product using an invalid ID
    // ---------------------------------------------------------
    @Test
    void shouldNotRetrieveProductUsingInvalidId() throws Exception {

        mockMvc.perform(get("/api/products/{id}", "INVALID-PRODUCT-ID"))
                .andExpect(status().isNotFound());
    }

    // ---------------------------------------------------------
    // TC05 - Successful update of an existing product
    // ---------------------------------------------------------
    @Test
    void shouldUpdateExistingProductSuccessfully() throws Exception {

        Product product = new Product(
                testProductId,
                "Test Laptop",
                "Laptop created for testing",
                50000
        );

        // Add product first
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(product)))
                .andExpect(status().isCreated());

        Product updatedProduct = new Product(
                testProductId,
                "Updated Laptop",
                "Updated laptop description",
                60000
        );

        // Update product
        mockMvc.perform(put("/api/products/{id}", testProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testProductId)))
                .andExpect(jsonPath("$.name", is("Updated Laptop")))
                .andExpect(jsonPath("$.description", is("Updated laptop description")))
                .andExpect(jsonPath("$.price", is(60000.0)));
    }

    // ---------------------------------------------------------
    // TC06 - Attempt to update a non-existent product
    // ---------------------------------------------------------
    @Test
    void shouldNotUpdateNonExistentProduct() throws Exception {

        Product product = new Product(
                "NON-EXISTENT-ID",
                "Test Product",
                "Product for negative testing",
                1000
        );

        mockMvc.perform(put("/api/products/{id}", "NON-EXISTENT-ID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());
    }

    // ---------------------------------------------------------
    // TC07 - Successful deletion of a product
    // ---------------------------------------------------------
    @Test
    void shouldDeleteProductSuccessfully() throws Exception {

        Product product = new Product(
                testProductId,
                "Test Laptop",
                "Laptop created for testing",
                50000
        );

        // Add product first
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(product)))
                .andExpect(status().isCreated());

        // Delete product
        mockMvc.perform(delete("/api/products/{id}", testProductId))
                .andExpect(status().isNoContent());
    }

    // ---------------------------------------------------------
    // TC08 - Verify deleted product cannot be retrieved
    // ---------------------------------------------------------
    @Test
    void shouldNotRetrieveDeletedProduct() throws Exception {

        Product product = new Product(
                testProductId,
                "Test Laptop",
                "Laptop created for testing",
                50000
        );

        // Add product first
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(product)))
                .andExpect(status().isCreated());

        // Delete product
        mockMvc.perform(delete("/api/products/{id}", testProductId))
                .andExpect(status().isNoContent());

        // Try to retrieve deleted product
        mockMvc.perform(get("/api/products/{id}", testProductId))
                .andExpect(status().isNotFound());
    }
}