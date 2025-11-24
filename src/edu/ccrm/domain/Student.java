package edu.ccrm.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    private final String id;
    private final String regNo;
    private StudentStatus status;
    private final List<Course> enrolledCourses;
    private final LocalDateTime registrationDate;
    private LocalDateTime lastUpdated;

    public enum StudentStatus {
        ACTIVE,
        INACTIVE,
        GRADUATED
    }

    public Student(String id, String regNo, String fullName, String email, LocalDate dateOfBirth) {
        super(fullName, email, dateOfBirth);
        this.id = id;
        this.regNo = regNo;
        this.status = StudentStatus.ACTIVE;
        this.enrolledCourses = new ArrayList<>();
        this.registrationDate = LocalDateTime.now();
        this.lastUpdated = this.registrationDate;
    }

    @Override
    public String getProfile() {
        return String.format("Student Profile:%nID: %s%nReg No: %s%nName: %s%nEmail: %s%nStatus: %s%n",
            this.id, this.regNo, this.fullName, this.email, this.status);
    }

    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            this.lastUpdated = LocalDateTime.now();
        }
    }
    
    public void unenrollFromCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            this.lastUpdated = LocalDateTime.now();
        }
    }

    public String getId() {
        return id;
    }

    public String getRegNo() {
        return regNo;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
        this.lastUpdated = LocalDateTime.now();
    }

    public List<Course> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}