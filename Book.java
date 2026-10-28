/** One book title in a library catalog. */
public class Book {
    private final String title;
    private final int totalCopies;
    private int checkedOut;

    public Book(String title, int totalCopies, int checkedOut) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be blank.");
        }
        if (totalCopies < 0 || checkedOut < 0 || checkedOut > totalCopies) {
            throw new IllegalArgumentException("Invalid book copy counts.");
        }
        this.title = title.trim();
        this.totalCopies = totalCopies;
        this.checkedOut = checkedOut;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableCopies() {
        return totalCopies - checkedOut;
    }

    public void checkOut() {
        if (getAvailableCopies() == 0) {
            throw new IllegalArgumentException("No available copies to check out.");
        }
        checkedOut++;
    }

    public void returnCopy() {
        if (checkedOut == 0) {
            throw new IllegalArgumentException("No checked-out copies to return.");
        }
        checkedOut--;
    }

    @Override
    public String toString() {
        return title + ": " + getAvailableCopies() + " of " + totalCopies + " copies available";
    }
}
