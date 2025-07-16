package com.example.tshirtstore.controller;

import com.example.tshirtstore.dto.ProductDto;
import com.example.tshirtstore.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product-form";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        
        productService.saveProduct(productDto);
        return "redirect:/admin?productAdded";
    }

    @PostMapping("/update-price")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updatePrice(@RequestParam Long id, 
                            @RequestParam double newPrice) {
        productService.updateProductPrice(id, newPrice);
        return "redirect:/admin?priceUpdated";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin?productDeleted";
    }
}