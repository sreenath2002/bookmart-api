package com.example.bookmart.project.Service;

import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EntityIdService {




    @Autowired

    private UniversityRepository universityRepository;

    @Autowired
    private CourseRepository courseRepository;



    @Autowired
    private  SubjectRepository subjectRepository;



  @Autowired
  private CategoryRepository categoryRepository;



  @Autowired
  private SemesterRepository semesterRepository;



    public University findUniversity(String name) {
        return universityRepository.findByUniversityName(name);
    }

    // Method to find Subject by name
    public Subject findSubject(String name) {
        return subjectRepository.findBySubjectName(name);
    }

    public Course findCourse(String name) {
        return courseRepository.findByCourseName(name);
    }



    // Method to find Category by name
    public Category findCategory(String name) {
        return categoryRepository.findByName(name);
    }

    public Semester findSemester(String name) {
        return semesterRepository.findByName(name);
    }



}
