package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Cupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuponRepository extends JpaRepository<Cupons,Long> {

    Cupons findByCode(String code);

    @Query("SELECT COUNT(c) FROM Cupons c WHERE c.is_activeState = :isActive")
    long countByIsActiveState(@Param("isActive") boolean isActive);
}
