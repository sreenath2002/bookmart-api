package com.example.bookmart.project.Request;

public class AddToCartRequest {
    private Long userId;
    private Long productId;
    private int quantity;

    // Default constructor
    public AddToCartRequest() {
    }

    // Parameterized constructor
//    public AddToCartRequest(Long userId, Long productId, int quantity) {
//        this.userId = userId;
//        this.productId = productId;
//        this.quantity = quantity;
//    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

