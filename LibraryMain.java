import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Console client for a library catalog stored as CSV. */
public class LibraryMain {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("This program reads a library catalog file and lists the books that are running low.");
        System.out.println("A book is low when fewer than 2 copies are available.");
        System.out.println();

        Scanner fileScanner = null;
        while (fileScanner == null) {
            System.out.print("Catalog file name? ");
            if (!console.hasNextLine()) {
                System.out.println("No file name supplied.");
                return;
            }
            String fileName = console.nextLine().trim();
            try {
                fileScanner = new Scanner(new File(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Try again.");
            }
        }

        List<Book> books = new ArrayList<>();
        int lineNumber = 0;
        try (Scanner catalog = fileScanner) {
            while (catalog.hasNextLine()) {
                String line = catalog.nextLine().trim();
                lineNumber++;
                if (line.isEmpty()) {
                    continue;
                }
                // The first nonempty row is a header, not book data.
                if (books.isEmpty() && lineNumber == 1 && line.toLowerCase().startsWith("title,")) {
                    continue;
                }
                // Use a comma delimiter instead of Scanner's default whitespace delimiter,
                // so multiword book titles stay together as a single value.
                try (Scanner row = new Scanner(line)) {
                    row.useDelimiter("\\s*,\\s*");
                    if (!row.hasNext()) {
                        throw new IllegalArgumentException("Missing title");
                    }
                    String title = row.next().trim();
                    if (!row.hasNextInt()) {
                        throw new IllegalArgumentException("Missing or invalid total copies");
                    }
                    int total = row.nextInt();
                    if (!row.hasNextInt()) {
                        throw new IllegalArgumentException("Missing or invalid checked-out copies");
                    }
                    int checkedOut = row.nextInt();
                    if (row.hasNext()) {
                        throw new IllegalArgumentException("Too many columns");
                    }
                    books.add(new Book(title, total, checkedOut));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid row " + lineNumber + ": " + e.getMessage());
                }
            }
        }

        System.out.println();
        System.out.println("Books running low:");
        int lowCount = 0;
        for (Book book : books) {
            if (book.getAvailableCopies() < 2) {
                System.out.println("  " + book);
                lowCount++;
            }
        }
        if (lowCount == 0) {
            System.out.println("  None");
        }
        System.out.println(books.size() + " books in the catalog, " + lowCount + " running low.");
    }
}
