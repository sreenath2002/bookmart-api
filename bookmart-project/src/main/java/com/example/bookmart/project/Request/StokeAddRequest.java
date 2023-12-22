package com.example.bookmart.project.Request;

public class StokeAddRequest {

    private Long productId;

    private int qtyLeft;

    private int qtyTotal;

    public StokeAddRequest()
    {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQtyLeft() {
        return qtyLeft;
    }

    public void setQtyLeft(int qtyLeft) {
        this.qtyLeft = qtyLeft;
    }

    public int getQtyTotal() {
        return qtyTotal;
    }

    public void setQtyTotal(int qtyTotal) {
        this.qtyTotal = qtyTotal;
    }
}
