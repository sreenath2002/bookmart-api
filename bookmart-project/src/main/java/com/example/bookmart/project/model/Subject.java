package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="subject_name")
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    //

    public  Subject()
    {

    }

    public Subject(Long id, String subjectName, Course course) {
        this.id = id;
        this.subjectName = subjectName;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
