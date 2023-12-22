package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.CancelledProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelledProductsRepository extends JpaRepository<CancelledProducts,Long> {
}
