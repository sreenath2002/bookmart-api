package com.example.bookmart.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;



@Entity
public class StockManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne // Assuming each StockManagement record belongs to one Product
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties("stockManagement")
    private Product product;

    @Column(name = "qty_left")
    private int left;

    @Column(name = "total_quantity")
    private int total;

    public  StockManagement()
    {

    }

    public StockManagement(Long id, Product product, int left, int total) {
        this.id = id;
        this.product = product;
        this.left = left;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

