package com.crochetfinds.web;

import com.ecommerceapp.cart.Cart;
import com.ecommerceapp.cart.CartService;
import com.ecommerceapp.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = getCurrentUser(session);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to view your cart");
            return "redirect:/login";
        }

        Cart cart = cartService.getOrCreateCart(user);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            @RequestParam(required = false) String redirectTo,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        User user = getCurrentUser(session);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to add items to cart");
            return "redirect:/login";
        }

        try {
            cartService.addItemToCart(user, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Item added to cart");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        // Redirect to the page where the add action was triggered
        if (redirectTo != null && !redirectTo.isEmpty()) {
            return "redirect:" + redirectTo;
        }
        return "redirect:/products";
    }
    
    @PostMapping("/cart/update-quantity")
    public String updateQuantityFromProduct(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String redirectTo,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        try {
            cartService.getCartItemByProductId(user, productId)
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        cartService.removeItemFromCart(user, item.getId());
                    } else {
                        cartService.updateCartItemQuantity(user, item.getId(), quantity);
                    }
                });
            redirectAttributes.addFlashAttribute("success", "Cart updated");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        if (redirectTo != null && !redirectTo.isEmpty()) {
            return "redirect:" + redirectTo;
        }
        return "redirect:/";
    }

    @PostMapping("/cart/update")
    public String updateCartItem(
            @RequestParam Long cartItemId,
            @RequestParam Integer quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        try {
            cartService.updateCartItemQuantity(user, cartItemId, quantity);
            redirectAttributes.addFlashAttribute("success", "Cart updated");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(
            @RequestParam Long cartItemId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        User user = getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        try {
            cartService.removeItemFromCart(user, cartItemId);
            redirectAttributes.addFlashAttribute("success", "Item removed from cart");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }
}
