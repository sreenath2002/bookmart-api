package com.example.bookmart.project.Request;

public class AddProfileImageRequest {

    private Long userId;

    private String imageUrl;

    public  AddProfileImageRequest(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
