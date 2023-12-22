package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class CancelResons {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @Column
    private String reason;

    public CancelResons(){

    }

    public CancelResons(Long id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
