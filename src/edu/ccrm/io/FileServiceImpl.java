package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths; // Import Paths
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ccrm.domain.Course;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;

public class FileServiceImpl implements FileService {

    private final StudentService studentService;
    private final CourseService courseService;

    public FileServiceImpl(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    public void importStudents(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1)
                 .filter(line -> !line.isBlank())
                 .map(line -> line.split(","))
                 .filter(data -> data.length >= 4)
                 .forEach(data -> {
                     studentService.addStudent(
                         UUID.randomUUID().toString(), 
                         data[0].trim(), 
                         data[1].trim(), 
                         data[2].trim(), 
                         LocalDate.parse(data[3].trim())
                     );
                 });
        }
    }

    @Override
    public void importCourses(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1)
                 .filter(line -> !line.isBlank())
                 .map(line -> line.split(","))
                 .filter(data -> data.length >= 2)
                 .forEach(data -> {
                     Course course = new Course.CourseBuilder(data[0].trim(), data[1].trim()).build();
                     courseService.addCourse(course);
                 });
        }
    }

    @Override
    public void exportData(Path studentsPath, Path coursesPath, Path enrollmentsPath) throws IOException {
        List<String> studentLines = studentService.getAllStudents().stream()
            .map(s -> String.join(",", s.getRegNo(), s.getFullName(), s.getEmail(), s.getStatus().toString()))
            .collect(Collectors.toList());
        studentLines.add(0, "RegNo,FullName,Email,Status");
        Files.write(studentsPath, studentLines);

        List<String> courseLines = courseService.getAllCourses().stream()
            .map(c -> String.join(",", c.getCode(), c.getTitle(), String.valueOf(c.getCredits())))
            .collect(Collectors.toList());
        courseLines.add(0, "Code,Title,Credits");
        Files.write(coursesPath, courseLines);
        
        List<String> enrollmentLines = studentService.getAllStudents().stream()
            .flatMap(s -> s.getEnrolledCourses().stream().map(c -> s.getRegNo() + "," + c.getCode()))
            .collect(Collectors.toList());
        enrollmentLines.add(0, "StudentRegNo,CourseCode");
        Files.write(enrollmentsPath, enrollmentLines);
    }

    @Override
    public Path backupData(Path sourceDirectory) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        
        // FIX: This logic is changed to prevent the NullPointerException.
        // It now creates the "backups" folder at the project root.
        Path backupRoot = Paths.get("backups");
        Path backupDir = backupRoot.resolve("backup_" + timestamp);
        
        Files.createDirectories(backupDir);

        try (Stream<Path> files = Files.walk(sourceDirectory)) {
            files.filter(Files::isRegularFile)
                 .forEach(sourceFile -> {
                     try {
                         Path destinationFile = backupDir.resolve(sourceDirectory.relativize(sourceFile));
                         Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                     } catch (IOException e) {
                         System.err.println("Failed to backup file: " + sourceFile);
                     }
                 });
        }
        return backupDir;
    }
}