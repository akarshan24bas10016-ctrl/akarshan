package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException; // Add this import

public interface EnrollmentService {
    boolean enrollStudentInCourse(Student student, Course course) throws DuplicateEnrollmentException;
    
    boolean unenrollStudentFromCourse(Student student, Course course);
}