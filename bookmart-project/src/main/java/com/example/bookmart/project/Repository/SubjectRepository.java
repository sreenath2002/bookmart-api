package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Course;
import com.example.bookmart.project.model.Semester;
import com.example.bookmart.project.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
   public Subject findBySubjectName(String subjectName);



   List<Subject> findByCourse(Course course);

//   @Query("SELECT s FROM Subject s WHERE s.name = :subjectName AND s.course = :course")

  Subject findBySubjectNameAndCourse(String subjectName, Course course);
}
