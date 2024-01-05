package com.example.bookmart.project.Service;


import com.example.bookmart.project.Repository.ReviewRepository;
import com.example.bookmart.project.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Reviewservice {

    private final ReviewRepository reviewRepository;

    public Reviewservice(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public List<Review> getAllReviewsByProductId(Long productId) {
        return reviewRepository.findByProduct_Id(productId);
    }
}
