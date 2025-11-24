package edu.ccrm.service;

import java.util.List;
import java.util.function.Predicate;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;

public interface CourseService {
    Course addCourse(Course course);
    Course findCourseByCode(String courseCode);
    List<Course> getAllCourses();
    void assignInstructorToCourse(Instructor instructor, Course course);
    List<Course> searchCourses(Predicate<Course> predicate);
}