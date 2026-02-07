package com.crochetfinds.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerceapp.cart.Cart;
import com.ecommerceapp.cart.CartService;
import com.ecommerceapp.product.Product;
import com.ecommerceapp.product.ProductService;
import com.ecommerceapp.user.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) String minRating,
            Model model,
            HttpSession session) {
        
        // Parse filter parameters
        BigDecimal minPriceValue = null;
        BigDecimal maxPriceValue = null;
        Double minRatingValue = null;
        
        try {
            if (minPrice != null && !minPrice.isEmpty()) {
                minPriceValue = new BigDecimal(minPrice);
            }
            if (maxPrice != null && !maxPrice.isEmpty()) {
                maxPriceValue = new BigDecimal(maxPrice);
            }
            if (minRating != null && !minRating.isEmpty()) {
                minRatingValue = Double.parseDouble(minRating);
            }
        } catch (NumberFormatException e) {
            // Invalid number format, ignore the filter
        }
        
        // Get filtered products
        List<Product> products = productService.getFilteredProducts(
            category, minPriceValue, maxPriceValue, minRatingValue);
        
        // Get all categories for filter dropdown
        List<String> categories = productService.getAllCategories();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedMinPrice", minPrice);
        model.addAttribute("selectedMaxPrice", maxPrice);
        model.addAttribute("selectedMinRating", minRating);
        
        // Get cart information if user is logged in
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Cart cart = cartService.getOrCreateCart(user);
            // Create a map of productId -> quantity for quick lookup
            Map<Long, Integer> cartQuantities = new HashMap<>();
            if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                for (var item : cart.getItems()) {
                    cartQuantities.put(item.getProduct().getId(), item.getQuantity());
                }
            }
            model.addAttribute("cartQuantities", cartQuantities);
        }
        
        return "products";
    }
}
