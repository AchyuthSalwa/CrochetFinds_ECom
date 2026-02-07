package com.crochetfinds.web;

import com.ecommerceapp.product.Product;
import com.ecommerceapp.product.ProductService;
import com.ecommerceapp.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    private boolean isAdmin(User user) {
        return user != null && Boolean.TRUE.equals(user.getIsAdmin());
    }

    private String checkAdminAndRedirect(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = getCurrentUser(session);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to access admin panel");
            return "redirect:/login";
        }
        if (!isAdmin(user)) {
            redirectAttributes.addFlashAttribute("error", "Access denied. Admin privileges required.");
            return "redirect:/";
        }
        return null;
    }

    @GetMapping("/products")
    public String adminProducts(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String redirect = checkAdminAndRedirect(session, redirectAttributes);
        if (redirect != null) return redirect;

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String newProduct(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String redirect = checkAdminAndRedirect(session, redirectAttributes);
        if (redirect != null) return redirect;

        model.addAttribute("product", new Product());
        model.addAttribute("categories", productService.getAllCategories());
        return "admin/product-form";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        String redirect = checkAdminAndRedirect(session, redirectAttributes);
        if (redirect != null) return redirect;

        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        
        model.addAttribute("product", product);
        model.addAttribute("categories", productService.getAllCategories());
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) BigDecimal discount,
            @RequestParam Integer stock,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) Double rating,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        String redirect = checkAdminAndRedirect(session, redirectAttributes);
        if (redirect != null) return redirect;

        try {
            Product product;
            if (id != null) {
                // Update existing product
                product = productService.getProductById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
            } else {
                // Create new product
                product = new Product();
            }
            
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setDiscount(discount != null ? discount : BigDecimal.ZERO);
            product.setStock(stock);
            product.setCategory(category);
            // Only update imageUrl if a new one is provided, otherwise keep existing
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                product.setImageUrl(imageUrl);
            } else if (id == null) {
                // For new products, set to null if no URL provided
                product.setImageUrl(null);
            }
            // If id != null and imageUrl is empty, keep existing imageUrl (don't change it)
            product.setRating(rating != null ? rating : 0.0);
            
            if (id != null) {
                productService.updateProduct(id, product);
                redirectAttributes.addFlashAttribute("success", "Product updated successfully");
            } else {
                productService.createProduct(product);
                redirectAttributes.addFlashAttribute("success", "Product created successfully");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving product: " + e.getMessage());
        }

        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        String redirect = checkAdminAndRedirect(session, redirectAttributes);
        if (redirect != null) return redirect;

        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting product: " + e.getMessage());
        }

        return "redirect:/admin/products";
    }
}
