package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.StockManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockManagementRepository extends JpaRepository<StockManagement,Long> {
}
