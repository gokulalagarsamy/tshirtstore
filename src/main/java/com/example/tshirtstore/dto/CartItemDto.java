package com.example.tshirtstore.dto;

public class CartItemDto {

    private Long productId;
    private int quantity;

    // No-args constructor
    public CartItemDto() {
    }

    // All-args constructor
    public CartItemDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter for productId
    public Long getProductId() {
        return productId;
    }

    // Setter for productId
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // Getter for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
