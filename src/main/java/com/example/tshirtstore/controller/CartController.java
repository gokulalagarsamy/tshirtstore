package com.example.tshirtstore.controller;

import com.example.tshirtstore.dto.CartItemDto;
import com.example.tshirtstore.model.User;
import com.example.tshirtstore.service.CartService;
import com.example.tshirtstore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("cartItems", cartService.getUserCart(user));
        model.addAttribute("user", user);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@ModelAttribute CartItemDto cartItemDto,
                          Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        cartService.addToCart(user, cartItemDto);
        return "redirect:/user?addedToCart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "redirect:/cart?itemRemoved";
    }
}
