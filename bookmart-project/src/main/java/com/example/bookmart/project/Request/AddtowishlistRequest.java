package com.example.bookmart.project.Request;

public class AddtowishlistRequest {
    private Long userId;
    private Long productId;
    private int quantity;

    public AddtowishlistRequest()
    {

    }

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
