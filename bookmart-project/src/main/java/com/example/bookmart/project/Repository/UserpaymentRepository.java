package com.example.bookmart.project.repository;


import com.example.bookmart.project.model.Userpaymet;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserpaymentRepository extends JpaRepository<Userpaymet, Long> {
}

