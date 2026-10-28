/** Represents one student's course grades. */
public class Student {
    private final String name;
    private double totalGradePoints;
    private int courseCount;

    public Student(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be blank.");
        }
        this.name = name.trim();
        totalGradePoints = 0;
        courseCount = 0;
    }

    public String getName() {
        return name;
    }

    public void addCourse(double gradePoints) {
        if (!Double.isFinite(gradePoints) || gradePoints < 0.0 || gradePoints > 4.0) {
            throw new IllegalArgumentException("Grade points must be between 0.0 and 4.0.");
        }
        totalGradePoints += gradePoints;
        courseCount++;
    }

    public double getGPA() {
        return courseCount == 0 ? 0.0 : totalGradePoints / courseCount;
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "%s: %.2f", name, getGPA());
    }
}
