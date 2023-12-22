package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @Column(name="status_name")
    private String name;

    public Status()
    {

    }



    public Status(Long id, String status) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
