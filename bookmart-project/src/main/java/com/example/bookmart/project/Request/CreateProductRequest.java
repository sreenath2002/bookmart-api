package com.example.bookmart.project.Request;

import com.example.bookmart.project.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateProductRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long userId;

    @NotNull
    private int price;

    private int discountedPrice;

    private int discountPresent;

    @NotNull
    private int quantity;



    private String subjects;

    private String courses;

    private String universitys;

//    private String type;

    private String authors;

    private List<String> imageUrls;
//    private int numRatings;

    @NotNull
    private String parentCategory; // Represents the parent category ID (e.g., UG, PG)



//    @NotNull
//    private String subCategory;// Represents the category ID (e.g., Science, Commerce)

    @NotNull
    private String semester;

    public CreateProductRequest(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getDiscountPresent() {
        return discountPresent;
    }

    public void setDiscountPresent(int discountPresent) {
        this.discountPresent = discountPresent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getUniversitys() {
        return universitys;
    }

    public void setUniversitys(String universitys) {
        this.universitys = universitys;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

//    public String getSubCategory() {
//        return subCategory;
//    }
//
//    public void setSubCategory(String subCategory) {
//        this.subCategory = subCategory;
//    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}