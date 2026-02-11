package StudentManagementProject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {

    private static ArrayList<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.dat";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        loadFromFile();   // Load previous data if exists

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewStudents(); break;
                case 3: searchStudent(); break;
                case 4: updateStudent(); break;
                case 5: deleteStudent(); break;
                case 6:
                    saveToFile();
                    System.out.println("Data saved successfully. Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            // Check duplicate ID
            for (Student s : students) {
                if (s.getId() == id) {
                    System.out.println("Student with this ID already exists!");
                    return;
                }
            }

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            students.add(new Student(id, name, age, course));
            saveToFile();
            System.out.println("Student added successfully!");

        } catch (Exception e) {
            System.out.println("Invalid input! Please try again.");
            sc.nextLine();
        }
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    private static void searchStudent() {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.getId() == id) {
                s.display();
                return;
            }
        }

        System.out.println("Student not found!");
    }

    private static void updateStudent() {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getId() == id) {

                System.out.print("Enter New Name: ");
                s.setName(sc.nextLine());

                System.out.print("Enter New Age: ");
                s.setAge(sc.nextInt());
                sc.nextLine();

                System.out.print("Enter New Course: ");
                s.setCourse(sc.nextLine());

                saveToFile();
                System.out.println("Student updated successfully!");
                return;
            }
        }

        System.out.println("Student not found!");
    }

    private static void deleteStudent() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        boolean removed = students.removeIf(s -> s.getId() == id);

        if (removed) {
            saveToFile();
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void saveToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(FILE_NAME));
            oos.writeObject(students);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(FILE_NAME));
            students = (ArrayList<Student>) ois.readObject();
            ois.close();
            System.out.println("Previous data loaded successfully.");
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
        }
    }
}

