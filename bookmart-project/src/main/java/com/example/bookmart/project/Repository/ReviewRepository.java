package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByProduct_Id(Long productId);
}
