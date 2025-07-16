package com.example.tshirtstore.dto;

public class OrderDto {
    private String shippingAddress;

    // No-args constructor
    public OrderDto() {}

    // All-args constructor (optional)
    public OrderDto(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    // Getter
    public String getShippingAddress() {
        return shippingAddress;
    }

    // Setter
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
