package com.example.bookmart.project.Request;

import jakarta.validation.constraints.NotBlank;

public class UpdateCategoryRequest {
    @NotBlank
    private String name;

    public UpdateCategoryRequest(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
