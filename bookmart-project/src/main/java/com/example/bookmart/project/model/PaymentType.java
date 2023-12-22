package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TypeName") // Use double quotes for the column name

    private String typeName;

    public PaymentType()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
