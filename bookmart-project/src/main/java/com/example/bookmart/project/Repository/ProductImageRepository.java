package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
