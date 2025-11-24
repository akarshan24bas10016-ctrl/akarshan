# Campus Course & Records Manager (CCRM)

## Project Overview
The Campus Course & Records Manager (CCRM) is a console-based Java application designed to manage the core academic operations of an educational institute. It provides administrators with the tools to manage student records, course catalogs, and academic enrollments in a simple and efficient command-line interface.

This project is built using **Java SE 21** and demonstrates a wide range of core Java principles, including Object-Oriented Programming (OOP), modern file I/O with NIO.2, the Stream API, robust exception handling, and fundamental design patterns.

## Features
* **Student Management**: Add, list, and find student records.
* **Course Management**: Create, list, and search/filter courses.
* **Enrollment Management**: Enroll students in available courses with duplicate-checking.
* **Data Persistence**: Import and export student and course data from/to simple `.csv` files.
* **Backup Utility**: Create timestamped backups of all application data and recursively calculate the total size of the backup directory.

---

## Setup and Installation

### Prerequisites
* Java Development Kit (JDK) version 21 or higher.
* Eclipse IDE for Java Developers or a similar IDE.

### How to Run
1.  Clone the repository to your local machine.
2.  Open the project in Eclipse IDE.
3.  Locate the `CliManager.java` file in the **`edu.ccrm.cli`** package.
4.  Right-click on the `CliManager.java` file and select **Run As > Java Application**.
5.  The application will start running in the Eclipse console.

---

## Windows JDK Installation Steps
1.  Download the Java JDK installer for Windows from the official Oracle website.
2.  Run the installer and follow the on-screen instructions.
3.  After installation, configure the `JAVA_HOME` and `Path` environment variables to point to your JDK installation directory (e.g., `C:\Program Files\Java\jdk-21`).
4.  Verify the installation by opening a Command Prompt and running `java -version`.

## Eclipse IDE Project Setup
1.  Launch Eclipse IDE and choose a workspace directory.
2.  Go to **File > New > Java Project**.
3.  Enter a project name (e.g., `CCRM`) and select the appropriate JRE version (e.g., `JavaSE-21`).
4.  Click **Finish**. The project will be created and will appear in the "Package Explorer".

---

## Java Core Concepts

### Evolution of Java (Key Milestones)
* **JDK 1.0 (1996)**: The initial release of Java by Sun Microsystems.
* **J2SE 5.0 (2004)**: A major update that added Generics, Enums, Annotations, and the for-each loop.
* **Java SE 8 (2014)**: A landmark release that introduced Lambda Expressions, the Stream API, and a new Date and Time API.
* **Java SE 17 (2021)**: A popular Long-Term Support (LTS) release bringing features like Sealed Classes.
* **Java SE 21 (2023)**: The latest LTS release, introducing major features like Virtual Threads and improvements to Pattern Matching.

### Java Platforms: ME vs SE vs EE
* **Java ME (Micro Edition)**: For small, resource-constrained devices like older mobile phones and IoT devices.
* **Java SE (Standard Edition)**: The core Java platform used for developing desktop, server, and console applications. **This project is built using Java SE.**
* **Java EE (Enterprise Edition)**: Built on top of Java SE, it provides APIs for building large-scale, distributed, and web-based applications (now known as Jakarta EE).

### Java Architecture: JDK vs JRE vs JVM
* **JVM (Java Virtual Machine)**: The "engine" that provides the runtime environment to execute Java bytecode. It is responsible for converting bytecode into machine code for the specific platform.
* **JRE (Java Runtime Environment)**: The software package that contains the JVM and the necessary core libraries to *run* a Java application.
* **JDK (Java Development Kit)**: The full development kit for Java. It contains everything in the JRE, plus development tools like the compiler (`javac`) and debugger. A developer needs the JDK to *write* and *compile* Java code.

---

## Technical Requirements Mapping
| Requirement               | File / Method Where Demonstrated                                      |
| ------------------------- | --------------------------------------------------------------------- |
| **OOP - Inheritance** | `Student.java` and `Instructor.java` extend `Person.java`.            |
| **OOP - Abstraction** | `Person.java` is an abstract class with an abstract `getProfile()` method.|
| **OOP - Polymorphism** | The `getProfile()` method is overridden in `Student` and `Instructor`.|
| **OOP - Encapsulation** | All domain classes (`Student`, `Course`, etc.) use private fields.   |
| **Design Pattern - Singleton** | `DataStore.java` uses a static `getInstance()` method.              |
| **Design Pattern - Builder** | `Course.java` contains a nested `CourseBuilder` static class.       |
| **Exception Handling (Custom)** | `DuplicateEnrollmentException.java` is thrown and caught.         |
| **File I/O (NIO.2)** | `FileServiceImpl.java` uses `Path`, `Paths`, and `Files` for all operations.|
| **Recursion** | `FileUtils.java` has the `calculateDirectorySize()` method.         |
| **Java Stream API** | `FileServiceImpl.java` uses streams to read CSV files and `CourseServiceImpl.java` uses them to filter courses. |
| **Date/Time API** | `FileServiceImpl.java` uses `LocalDateTime` for backup timestamps.    |
| **Enums with Fields** | `Grade.java` and `Semester.java` have constructors and fields.        |
| **Lambdas** | Used in all Stream API calls, e.g., `.filter(line -> !line.isBlank())`. |

---

## Usage and Commands

Below is a typical workflow for using the application:

1.  **Import Data**:
    * Navigate: Main Menu -> `4. File Operations` -> `1. Import Students from CSV`
    * This will load the sample student data from `data/students.csv`.

2.  **Add a Course**:
    * Navigate: Main Menu -> `2. Manage Courses` -> `1. Add New Course`
    * Follow the prompts to enter the course details.

3.  **Enroll a Student**:
    * Navigate: Main Menu -> `3. Manage Enrollment`
    * Follow the prompts to enter a Student Registration Number and a Course Code.

4.  **Export and Backup Data**:
    * Navigate: Main Menu -> `4. File Operations` -> `3. Export All Data`
    * This will save the current state to new CSV files in the `data` folder.
    * Navigate: Main Menu -> `4. File Operations` -> `4. Backup Exported Data`
    * This will create a new timestamped backup folder in the `backups` directory.