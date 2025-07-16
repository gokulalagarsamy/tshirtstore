package com.example.tshirtstore.controller;

import com.example.tshirtstore.model.User;
import com.example.tshirtstore.service.ProductService;
import com.example.tshirtstore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final ProductService productService;
    private final UserService userService;

    public UserController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("products", productService.getAllProducts());
        return "user";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String address, 
                              Authentication authentication,
                              Model model) {
        String username = authentication.getName();
        try {
            userService.updateUserAddress(username, address);
            return "redirect:/profile?updateSuccess";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "profile";
        }
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam String query, 
                               Model model, 
                               Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("products", productService.searchProducts(query));
        return "user";
    }
}