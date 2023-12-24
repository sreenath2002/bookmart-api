package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Cupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuponRepository extends JpaRepository<Cupons,Long> {

}
