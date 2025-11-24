package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    void importStudents(Path filePath) throws IOException;
    void importCourses(Path filePath) throws IOException;
    void exportData(Path studentsPath, Path coursesPath, Path enrollmentsPath) throws IOException;
    Path backupData(Path sourceDirectory) throws IOException;
}