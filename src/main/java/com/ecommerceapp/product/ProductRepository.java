package com.ecommerceapp.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByName(String name);
    
    List<Product> findByCategory(String category);
    
    List<Product> findByStockGreaterThan(Integer stock);
    
    @Query("SELECT p FROM Product p WHERE p.stock > 0 ORDER BY p.rating DESC")
    List<Product> findAvailableProductsOrderByRatingDesc();
    
    @Query("SELECT p FROM Product p WHERE p.stock > 0 AND (:category IS NULL OR p.category = :category) AND (:minPrice IS NULL OR p.price >= :minPrice) AND (:maxPrice IS NULL OR p.price <= :maxPrice) AND (:minRating IS NULL OR p.rating >= :minRating) ORDER BY p.rating DESC, p.name ASC")
    List<Product> findFilteredProducts(@Param("category") String category, 
                                       @Param("minPrice") BigDecimal minPrice, 
                                       @Param("maxPrice") BigDecimal maxPrice,
                                       @Param("minRating") Double minRating);
    
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.category IS NOT NULL ORDER BY p.category")
    List<String> findAllDistinctCategories();
}
