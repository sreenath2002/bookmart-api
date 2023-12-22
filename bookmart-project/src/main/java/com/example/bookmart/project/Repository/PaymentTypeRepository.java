package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {
}
