package com.example.bookmart.project.Request;

public class CreateCourseCategoryRequest {

    private  String courses;

    private String parentCategory;

    public  CreateCourseCategoryRequest(){

    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }
}
