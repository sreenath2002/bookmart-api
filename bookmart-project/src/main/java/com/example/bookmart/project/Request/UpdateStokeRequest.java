package com.example.bookmart.project.Request;

public class UpdateStokeRequest {

    private int qtyLeft;

    private int qtyTotal;

    public  UpdateStokeRequest(){

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
