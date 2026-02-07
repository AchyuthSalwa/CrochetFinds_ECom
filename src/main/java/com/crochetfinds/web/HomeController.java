package com.crochetfinds.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerceapp.cart.Cart;
import com.ecommerceapp.cart.CartService;
import com.ecommerceapp.product.Product;
import com.ecommerceapp.product.ProductService;
import com.ecommerceapp.user.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Get top-rated available products, limited to 8
        List<Product> products = productService.getTopRatedAvailableProducts(8);
        model.addAttribute("products", products);
        
        // Get cart information if user is logged in
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Cart cart = cartService.getOrCreateCart(user);
            // Create a map of productId -> quantity for quick lookup
            Map<Long, Integer> cartQuantities = new HashMap<>();
            if (cart != null && cart.getItems() != null) {
                cart.getItems().forEach(item -> {
                    cartQuantities.put(item.getProduct().getId(), item.getQuantity());
                });
            }
            model.addAttribute("cartQuantities", cartQuantities);
        }
        
        return "index";
    }
}
