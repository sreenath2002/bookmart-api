package com.example.bookmart.project.Repository;


import com.example.bookmart.project.model.Userpaymet;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserpaymentRepository extends JpaRepository<Userpaymet, Long> {
}

