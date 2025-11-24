package edu.ccrm.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;

public class CourseServiceImpl implements CourseService {

    private final DataStore dataStore = DataStore.getInstance();

    @Override
    public Course addCourse(Course course) {
        if (dataStore.getCourse(course.getCode()) != null) {
            System.out.println("Error: Course with code " + course.getCode() + " already exists.");
            return null;
        }
        dataStore.addCourse(course);
        return course;
    }

    @Override
    public Course findCourseByCode(String courseCode) {
        return dataStore.getCourse(courseCode);
    }

    @Override
    public List<Course> getAllCourses() {
        return dataStore.getAllCourses();
    }

    @Override
    public void assignInstructorToCourse(Instructor instructor, Course course) {
        if (instructor != null && course != null) {
            course.setInstructor(instructor);
            instructor.assignCourse(course);
        }
    }

    @Override
    public List<Course> searchCourses(Predicate<Course> predicate) {
        return dataStore.getAllCourses().stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }
}