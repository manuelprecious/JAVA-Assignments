

import java.util.ArrayList;

public class StudentManager {
    public static void main(String[] args) {
        InputHelper input = new InputHelper();
        ArrayList<Student> students = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Search Stduent by Name");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            int choice = input.readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    String name = input.readLine("Enter name: ");
                    int age = input.readInt("Enter age: ");
                    String id = input.readLine("Enter Student Id: ");
                    students.add(new Student(name, age, id));
                    System.out.println("Student added!");
                    break;

                case 2:
                    if (students.isEmpty()) {
                        System.out.println("No students yet.");
                    } else {
                        System.out.println("Students:");
                        for (Student s : students) {
                            System.out.println(s);
                        }
                    }
                    break;

                case 3:
                    String searchName = input.readLine("Enter name to search: ");
                    boolean found = false;

                    for (Student s : students) {
                        if (s.getName().equalsIgnoreCase(searchName)) {
                            System.out.println("Found: " + s);
                            found = true;
                        }
                    }

                    if (!found)
                        System.out.println("No student found with the name.");
                    break;

                case 4:
                    String delId = input.readLine("Enter student ID to delete: ");
                    boolean deleted = students.removeIf(s -> s.getStudentId().equalsIgnoreCase(delId));
                    System.out.println(deleted ? "Student deleted." : "No matching ID found.");

                case 5:
                    input.close();
                    System.out.println("Goodbye!");
                default:
                    System.out.println("invalid choice. Try again.");
            }
        }
    }
}