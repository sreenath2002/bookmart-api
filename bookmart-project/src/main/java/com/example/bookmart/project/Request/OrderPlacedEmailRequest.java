package com.example.bookmart.project.Request;

import java.util.List;

public class OrderPlacedEmailRequest {
    private List<Long> productIdList;



    private List<Integer> qty;

    private List<Integer> price;


    private String enteredemail;
    public OrderPlacedEmailRequest(){

    }

    public List<Long> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Long> productIdList) {
        this.productIdList = productIdList;
    }

    public List<Integer> getQty() {
        return qty;
    }

    public void setQty(List<Integer> qty) {
        this.qty = qty;
    }

    public List<Integer> getPrice() {
        return price;
    }

    public void setPrice(List<Integer> price) {
        this.price = price;
    }




    public String getEnteredemail() {
        return enteredemail;
    }

    public void setEnteredemail(String enteredemail) {
        this.enteredemail = enteredemail;
    }
}
