package edu.ccrm.service;

import java.time.LocalDate;
import java.util.List;
import edu.ccrm.domain.Student;

public class StudentServiceImpl implements StudentService {

    private final DataStore dataStore = DataStore.getInstance();

    @Override
    public Student addStudent(String id, String regNo, String fullName, String email, LocalDate dateOfBirth) {
        if (dataStore.getStudent(regNo) != null) {
            // In a real app, we would throw an exception here
            System.out.println("Error: Student with registration number " + regNo + " already exists.");
            return null;
        }
        Student newStudent = new Student(id, regNo, fullName, email, dateOfBirth);
        dataStore.addStudent(newStudent);
        return newStudent;
    }

    @Override
    public Student findStudentByRegNo(String regNo) {
        return dataStore.getStudent(regNo);
    }

    @Override
    public List<Student> getAllStudents() {
        return dataStore.getAllStudents();
    }

    @Override
    public void updateStudentStatus(String regNo, Student.StudentStatus newStatus) {
        Student student = findStudentByRegNo(regNo);
        if (student != null) {
            student.setStatus(newStatus);
        } else {
            System.out.println("Error: Student not found.");
        }
    }
}