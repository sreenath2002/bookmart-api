package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Category;
import com.example.bookmart.project.model.Course;
//import com.example.bookmart.project.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//import java.util.Set;



public interface CourseRepository extends JpaRepository<Course, Long >{

    public Course findByCourseName(String courseName);


    List<Course> findByParentCategory(Category category);

    Long findIdByCourseName(String courses);





    boolean existsByCourseName(String courses);
}
