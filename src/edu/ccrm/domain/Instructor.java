package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {
    
    private final String staffId;
    private String department;
    private final List<Course> coursesTaught;
    
    public Instructor(String staffId, String fullName, String email, LocalDate dateOfBirth, String department) {
        super(fullName, email, dateOfBirth);
        this.staffId = staffId;
        this.department = department;
        this.coursesTaught = new ArrayList<>();
    }

    @Override
    public String getProfile() {
        return String.format("Instructor Profile:%nStaff ID: %s%nName: %s%nDepartment: %s%nEmail: %s%n",
            this.staffId, this.fullName, this.department, this.email);
    }
    
    public void assignCourse(Course course) {
        if (!coursesTaught.contains(course)) {
            coursesTaught.add(course);
        }
    }

    public String getStaffId() {
        return staffId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getCoursesTaught() {
        return new ArrayList<>(coursesTaught);
    }
}