package com.crochetfinds.web;

import com.ecommerceapp.order.Order;
import com.ecommerceapp.order.OrderService;
import com.ecommerceapp.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping("/orders")
    public String viewOrders(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = getCurrentUser(session);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to view your orders");
            return "redirect:/login";
        }

        List<Order> orders = orderService.getUserOrders(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = getCurrentUser(session);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to checkout");
            return "redirect:/login";
        }

        try {
            Order order = orderService.createOrderFromCart(user);
            redirectAttributes.addFlashAttribute("success", "Order placed successfully! Order ID: " + order.getId());
            return "redirect:/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }
}
