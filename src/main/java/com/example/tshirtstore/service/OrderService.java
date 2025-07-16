package com.example.tshirtstore.service;

import com.example.tshirtstore.dto.OrderDto;
import com.example.tshirtstore.model.*;
import com.example.tshirtstore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Transactional
    public Order placeOrder(User user, OrderDto orderDto) {
        List<Cart> cartItems = cartService.getUserCart(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setTotalAmount(totalAmount);
        order.setStatus("PROCESSING");

        // Convert cart items to order items
        List<OrderItem> orderItems = cartItems.stream().map(cart -> {
            OrderItem item = new OrderItem();
            item.setProduct(cart.getProduct());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getProduct().getPrice());
            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        
        Order savedOrder = orderRepository.save(order);
        cartService.clearUserCart(user);
        return savedOrder;
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }
}