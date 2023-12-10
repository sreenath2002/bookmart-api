package com.example.bookmart.project.Request;

public class UpdateCourseCategoryRequest {

    private String name;
     private String parentCategory;

     public  UpdateCourseCategoryRequest(){

     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }
}
