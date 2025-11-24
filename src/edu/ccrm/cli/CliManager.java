package edu.ccrm.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Predicate;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.io.FileService;
import edu.ccrm.io.FileServiceImpl;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.CourseServiceImpl;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.EnrollmentServiceImpl;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.StudentServiceImpl;
import edu.ccrm.util.FileUtils;

public class CliManager {

    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final FileService fileService;
    
    public CliManager() {
        this.scanner = new Scanner(System.in);
        this.studentService = new StudentServiceImpl();
        this.courseService = new CourseServiceImpl();
        this.enrollmentService = new EnrollmentServiceImpl();
        // CORRECTED LINE: The third argument (enrollmentService) has been removed.
        this.fileService = new FileServiceImpl(studentService, courseService);
    }

    public static void main(String[] args) {
        CliManager cli = new CliManager();
        cli.run();
    }

    public void run() {
        System.out.println("Welcome to the Campus Course & Records Manager (CCRM)");
        boolean running = true;
        do {
            displayMainMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1: manageStudents(); break;
                case 2: manageCourses(); break;
                case 3: manageEnrollment(); break;
                case 4: manageFileOperations(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid option. Please try again.");
            }
        } while (running);
        System.out.println("Thank you for using CCRM. Goodbye!");
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment");
        System.out.println("4. File Operations");
        System.out.println("5. Reports"); // Added Reports menu
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Find Student by Registration Number");
        System.out.print("Select an option: ");
        int choice = getUserChoice();

        switch(choice) {
            case 1:
                System.out.print("Enter Registration Number: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Full Name: ");
                String fullName = scanner.nextLine();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                try {
                    LocalDate dob = LocalDate.parse(scanner.nextLine());
                    studentService.addStudent(UUID.randomUUID().toString(), regNo, fullName, email, dob);
                    System.out.println("Student added successfully.");
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                }
                break;
            case 2:
                System.out.println("\n--- All Students ---");
                studentService.getAllStudents().forEach(s -> System.out.println(s.getProfile()));
                break;
            case 3:
                System.out.print("Enter Registration Number to find: ");
                String findRegNo = scanner.nextLine();
                Student student = studentService.findStudentByRegNo(findRegNo);
                if (student != null) {
                    System.out.println(student.getProfile());
                } else {
                    System.out.println("Student not found.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search/Filter Courses");
        System.out.print("Select an option: ");
        int choice = getUserChoice();

        switch(choice) {
            case 1:
                System.out.print("Enter Course Code: ");
                String code = scanner.nextLine();
                System.out.print("Enter Course Title: ");
                String title = scanner.nextLine();
                Course newCourse = new Course.CourseBuilder(code, title).build();
                courseService.addCourse(newCourse);
                System.out.println("Course added successfully.");
                break;
            case 2:
                System.out.println("\n--- All Courses ---");
                courseService.getAllCourses().forEach(System.out::println);
                break;
            case 3:
                System.out.print("Filter by department (leave blank for any): ");
                String dept = scanner.nextLine();
                Predicate<Course> filter = c -> true;
                if (!dept.isBlank()) {
                    filter = c -> c.getDepartment() != null && c.getDepartment().equalsIgnoreCase(dept);
                }
                System.out.println("\n--- Filtered Courses ---");
                courseService.searchCourses(filter).forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    private void manageEnrollment() {
        System.out.println("\n--- Enrollment Management ---");
        System.out.print("Enter Student Registration Number: ");
        String regNo = scanner.nextLine();
        Student student = studentService.findStudentByRegNo(regNo);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course course = courseService.findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        try {
            if (enrollmentService.enrollStudentInCourse(student, course)) {
                System.out.println("Enrollment successful!");
            }
        } catch (DuplicateEnrollmentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private void manageFileOperations() {
        System.out.println("\n--- File Operations ---");
        System.out.println("1. Import Students from CSV");
        System.out.println("2. Import Courses from CSV");
        System.out.println("3. Export All Data");
        System.out.println("4. Backup Exported Data");
        System.out.println("5. Calculate Backup Size (Recursive)");
        System.out.print("Select an option: ");
        int choice = getUserChoice();

        try {
            Path dataDir = Paths.get("data");
            Files.createDirectories(dataDir);

            switch(choice) {
                case 1:
                    fileService.importStudents(dataDir.resolve("students.csv"));
                    System.out.println("Students imported successfully.");
                    break;
                case 2:
                    fileService.importCourses(dataDir.resolve("courses.csv"));
                    System.out.println("Courses imported successfully.");
                    break;
                case 3:
                    fileService.exportData(
                        dataDir.resolve("export_students.csv"),
                        dataDir.resolve("export_courses.csv"),
                        dataDir.resolve("export_enrollments.csv")
                    );
                    System.out.println("Data exported to 'data' directory.");
                    break;
                case 4:
                    Path backupPath = fileService.backupData(dataDir);
                    System.out.println("Data backed up to: " + backupPath.toAbsolutePath());
                    break;
                case 5:
                    Path backupDir = Paths.get("backups");
                    if (Files.exists(backupDir)) {
                        long size = FileUtils.calculateDirectorySize(backupDir);
                        System.out.printf("Total size of backups directory: %.2f KB%n", size / 1024.0);
                    } else {
                        System.out.println("No backups directory found.");
                    }
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred during file operation: " + e.getMessage());
        }
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}