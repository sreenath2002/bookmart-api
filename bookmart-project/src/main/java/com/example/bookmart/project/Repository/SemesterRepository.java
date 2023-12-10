package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Category;
import com.example.bookmart.project.model.Course;
import com.example.bookmart.project.model.Semester;
import com.example.bookmart.project.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    public Semester findByName(String name);
    List<Semester> findByParentCategory(Category category);

    Semester findByNameAndParentCategory(String semester, Category parentCategory);
}
