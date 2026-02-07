package com.ecommerceapp.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

  
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    public List<Product> getTopRatedAvailableProducts(int limit) {
        List<Product> products = productRepository.findAvailableProductsOrderByRatingDesc();
        return products.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Product> getFilteredProducts(String category, BigDecimal minPrice, BigDecimal maxPrice, Double minRating) {
        return productRepository.findFilteredProducts(category, minPrice, maxPrice, minRating);
    }

    public List<String> getAllCategories() {
        return productRepository.findAllDistinctCategories();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());
        product.setImageUrl(productDetails.getImageUrl());
        product.setRating(productDetails.getRating());
        product.setDiscount(productDetails.getDiscount() != null ? productDetails.getDiscount() : BigDecimal.ZERO);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
