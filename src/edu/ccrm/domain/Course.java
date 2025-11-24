package edu.ccrm.domain;

import java.util.Objects;

public class Course {
    
    private final String code;
    private final String title;
    private final int credits;
    private final Semester semester;
    private final String department;
    private Instructor instructor;

    private Course(CourseBuilder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.semester = builder.semester;
        this.department = builder.department;
        this.instructor = builder.instructor;
    }

    public static class CourseBuilder {
        private final String code;
        private final String title;
        private int credits;
        private Semester semester;
        private String department;
        private Instructor instructor;

        public CourseBuilder(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public CourseBuilder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public CourseBuilder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public CourseBuilder department(String department) {
            this.department = department;
            return this;
        }
        
        public CourseBuilder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public Instructor getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        String instructorName = (instructor != null) ? instructor.getFullName() : "Not Assigned";
        return String.format("Course[Code: %s, Title: '%s', Credits: %d, Instructor: %s, Semester: %s]",
            code, title, credits, instructorName, semester);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}