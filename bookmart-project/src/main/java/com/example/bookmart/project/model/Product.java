package com.example.bookmart.project.model;

import com.example.bookmart.project.model.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "price")
    private int price;

    @Column(name = "discounted_price")
    private int discountedPrice;

    @Column(name = "discount_present")
    private int discountPresent;

    @OneToOne
    private StockManagement stockManagement;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("product")
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "subject_id",nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "university_id",nullable = false)
    private University university;


    @Column(name = "author")
    private String author;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name="nums_ratings")
    private int numRatings;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;



    @ManyToOne
    @JoinColumn(name = "semester_id") // Assuming a Product is associated with one Semester
    private Semester semester;

    private String status;
    private LocalDateTime createdAt;

    // Constructors
    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public StockManagement getStockManagement() {
        return stockManagement;
    }

    public void setStockManagement(StockManagement stockManagement) {
        this.stockManagement = stockManagement;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addImage(ProductImage productImage) {
    }
}


