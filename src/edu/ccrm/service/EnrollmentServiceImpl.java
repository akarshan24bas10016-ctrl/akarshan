package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException;

public class EnrollmentServiceImpl implements EnrollmentService {

	@Override
	public boolean enrollStudentInCourse(Student student, Course course) throws DuplicateEnrollmentException {
	    if (student == null || course == null) {
	        return false;
	    }
	    if (student.getEnrolledCourses().contains(course)) {
	        throw new DuplicateEnrollmentException("Student " + student.getRegNo() + " is already enrolled in " + course.getCode());
	    }
	    student.enrollInCourse(course);
	    return true;
	}

    @Override
    public boolean unenrollStudentFromCourse(Student student, Course course) {
        if (student == null || course == null) {
            return false;
        }
        if (!student.getEnrolledCourses().contains(course)) {
            System.out.println("Error: Student is not enrolled in this course.");
            return false;
        }
        student.unenrollFromCourse(course);
        return true;
    }
}