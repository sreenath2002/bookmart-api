package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UniversityRepository extends JpaRepository<University, Long> {

    public University findByUniversityName(String universityName);



}
