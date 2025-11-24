package edu.ccrm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;

public class DataStore {

    private static DataStore instance;

    private final Map<String, Student> students;
    private final Map<String, Course> courses;
    private final Map<String, Instructor> instructors;

    private DataStore() {
        students = new HashMap<>();
        courses = new HashMap<>();
        instructors = new HashMap<>();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public void addStudent(Student student) {
        students.put(student.getRegNo(), student);
    }

    public Student getStudent(String regNo) {
        return students.get(regNo);
    }


    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public void addInstructor(Instructor instructor) {
        instructors.put(instructor.getStaffId(), instructor);
    }

    public Instructor getInstructor(String staffId) {
        return instructors.get(staffId);
    }
    
    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors.values());
    }
}