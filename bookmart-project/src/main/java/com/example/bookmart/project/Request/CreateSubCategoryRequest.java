package com.example.bookmart.project.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateSubCategoryRequest {

    @NotNull
    @Size(max = 50)
    private String name;

    private Long parentCategoryId;


    public CreateSubCategoryRequest() {
    }

    public CreateSubCategoryRequest(String name, Long parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
