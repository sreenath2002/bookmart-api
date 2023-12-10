package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String name; // E.g., "Fall 2023", "Spring 2024", etc.

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category parentCategory;
    // Constructors
    public Semester() {
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
