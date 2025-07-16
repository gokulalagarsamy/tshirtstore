package com.example.tshirtstore.controller;

import com.example.tshirtstore.dto.OrderDto;
import com.example.tshirtstore.model.User;
import com.example.tshirtstore.service.OrderService;
import com.example.tshirtstore.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/place")
    public String placeOrder(@ModelAttribute OrderDto orderDto,
                           Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        orderService.placeOrder(user, orderDto);
        return "redirect:/orders?orderPlaced";
    }

    @GetMapping
    public String viewOrders(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("orders", orderService.getUserOrders(user));
        model.addAttribute("user", user);
        return "order";
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin-orders";
    }
}