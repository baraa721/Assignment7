import java.util.Scanner;

/** Console client for Student objects. */
public class StudentMain {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("This program computes the GPA of each student.");
        System.out.println("Enter each student's name (one word), then the grade points");
        System.out.println("of each course (4.0 = A, 3.0 = B, 2.0 = C, 1.0 = D, 0.0 = F).");

        int numberOfStudents = readNonnegativeInt(console, "How many students? ");
        Student[] students = new Student[numberOfStudents];
        for (int i = 0; i < students.length; i++) {
            String name = readName(console, "Student name: ");
            Student student = new Student(name);
            int numberOfCourses = readNonnegativeInt(console, "How many courses? ");
            for (int j = 1; j <= numberOfCourses; j++) {
                double grade = readDouble(console, "  Grade points for course " + j + ": ");
                student.addCourse(grade);
            }
            students[i] = student;
            System.out.println();
        }
        System.out.printf("%-18s %s%n", "Name", "GPA");
        for (Student student : students) {
            System.out.printf(java.util.Locale.US, "%-18s %.2f%n", student.getName(), student.getGPA());
        }
    }

    private static String readName(Scanner console, String prompt) {
        System.out.print(prompt);
        String name = console.nextLine().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be blank.");
        }
        return name;
    }

    private static int readNonnegativeInt(Scanner console, String prompt) {
        System.out.print(prompt);
        String input = console.nextLine().trim();
        int result = Integer.parseInt(input);
        if (result < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        return result;
    }

    private static double readDouble(Scanner console, String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(console.nextLine().trim());
    }
}
