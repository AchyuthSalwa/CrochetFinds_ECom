package com.ecommerceapp.order;

import com.ecommerceapp.cart.Cart;
import com.ecommerceapp.cart.CartService;
import com.ecommerceapp.product.ProductRepository;
import com.ecommerceapp.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrderFromCart(User user) {
        Cart cart = cartService.getOrCreateCart(user);
        
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty cart");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);

        // Convert cart items to order items
        cart.getItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getDiscountedPrice()); // Store discounted price at time of order
            
            // Update product stock
            int newStock = cartItem.getProduct().getStock() - cartItem.getQuantity();
            if (newStock < 0) {
                throw new IllegalStateException("Insufficient stock for product: " + cartItem.getProduct().getName());
            }
            cartItem.getProduct().setStock(newStock);
            productRepository.save(cartItem.getProduct());
            
            order.getItems().add(orderItem);
        });

        order.calculateTotalAmount();
        
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart after order creation
        cartService.clearCart(user);
        
        return savedOrder;
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }
}
