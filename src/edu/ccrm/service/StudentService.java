package edu.ccrm.service;

import java.time.LocalDate;
import java.util.List;
import edu.ccrm.domain.Student;

public interface StudentService {
    Student addStudent(String id, String regNo, String fullName, String email, LocalDate dateOfBirth);
    Student findStudentByRegNo(String regNo);
    List<Student> getAllStudents();
    void updateStudentStatus(String regNo, Student.StudentStatus newStatus);
}