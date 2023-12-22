package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation,Long> {
}
