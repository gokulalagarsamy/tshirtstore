package com.example.tshirtstore.service;

import com.example.tshirtstore.dto.CartItemDto;
import com.example.tshirtstore.model.Cart;
import com.example.tshirtstore.model.Product;
import com.example.tshirtstore.model.User;
import com.example.tshirtstore.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Transactional
    public void addToCart(User user, CartItemDto cartItemDto) {
        Product product = productService.getProductById(cartItemDto.getProductId());
        
        cartRepository.findByUserAndProduct(user, product).ifPresentOrElse(
            cart -> {
                cart.setQuantity(cart.getQuantity() + cartItemDto.getQuantity());
                cartRepository.save(cart);
            },
            () -> {
                Cart newCartItem = new Cart();
                newCartItem.setUser(user);
                newCartItem.setProduct(product);
                newCartItem.setQuantity(cartItemDto.getQuantity());
                cartRepository.save(newCartItem);
            }
        );
    }

    public List<Cart> getUserCart(User user) {
        return cartRepository.findByUser(user);
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearUserCart(User user) {
        cartRepository.deleteByUser(user);
    }
}