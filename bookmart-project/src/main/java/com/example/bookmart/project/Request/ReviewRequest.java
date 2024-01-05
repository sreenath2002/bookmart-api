package com.example.bookmart.project.Request;

public class ReviewRequest {
    private String review;
    private Long userId;

    private Integer rating;

    public ReviewRequest()
    {

    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
