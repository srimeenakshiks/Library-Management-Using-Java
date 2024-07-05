import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class User {
    private String username;
    private String password;
    private double fine;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.fine = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}
class Book {
    private String title;
    private String author;
    private String category;
    private boolean available;
    private LocalDate borrowedDate;
    private LocalDate dueDate;

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}

class Library {
    private ArrayList<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.remove(index);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Invalid book index.");
        }
    }

    public void displayBooks() {
        System.out.println("Available Books:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.isAvailable()) {
                System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    public ArrayList<Book> searchBooks(String keyword) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public ArrayList<Book> searchBooksByCategory(String category) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                results.add(book);
            }
        }
        return results;
    }

    public void returnBook(int index) {
        if (index >= 0 && index < books.size()) {
            Book book = books.get(index);
            if (!book.isAvailable()) {
                book.setAvailable(true);
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Book is already available.");
            }
        } else {
            System.out.println("Invalid book index.");
        }
    }

    public void borrowBook(int index) {
        if (index >= 0 && index < books.size()) {
            Book book = books.get(index);
            if (book.isAvailable()) {
                book.setAvailable(false);
                LocalDate borrowedDate = LocalDate.now();
                book.setBorrowedDate(borrowedDate);
                LocalDate dueDate = borrowedDate.plusDays(15); // Due date set to 15 days after borrowing
                book.setDueDate(dueDate);
                System.out.println("Book borrowed successfully. Return by: " + book.getDueDate());
            } else {
                System.out.println("Book is not available for borrowing.");
            }
        } else {
            System.out.println("Invalid book index.");
        }
    }

    public void calculateFine(int index) {
        if (index >= 1 && index <= books.size()) {
            Book book = books.get(index - 1);
            if (!book.isAvailable()) {
                LocalDate currentDate = LocalDate.now();
                long daysLate = ChronoUnit.DAYS.between(book.getDueDate(), currentDate);
                if (daysLate > 0) {
                    double fine = daysLate * 0.50; // Assuming a fine of 0.50 per day
                    System.out.println("Fine for returning the book late: $" + fine);
                } else {
                    System.out.println("No fine. The book was returned on time.");
                }
            } else {
                System.out.println("Book is already available.");
            }
        } else {
            System.out.println("Invalid book index.");
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        Map<String, User> users = new HashMap<>();


        boolean running = true;
        String currentUser = "";

        library.addBook(new Book("1984", "George Orwell", "Dystopian Fiction"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction"));
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction"));
        library.addBook(new Book("Pride and Prejudice", "Jane Austen", "Classic Fiction"));
        library.addBook(new Book("The Catcher in the Rye", "J.D. Salinger", "Fiction"));

        while (running) {
            if (currentUser.isEmpty()) {
                System.out.println("Welcome to the Library Management System.");
                System.out.println("1. Log in");
                System.out.println("2. Sign up");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int authChoice = scanner.nextInt();
                scanner.nextLine();

                switch (authChoice) {
                    case 1:
                        System.out.print("Enter your username: ");
                        String existingUser = scanner.nextLine();
                        if (users.containsKey(existingUser)) {
                            System.out.print("Enter your password: ");
                            String existingPassword = scanner.nextLine();
                            if (users.get(existingUser).equals(existingPassword)) {
                                currentUser = existingUser;
                                System.out.println("Authentication successful. Welcome, " + currentUser + "!");
                            } else {
                                System.out.println("Incorrect password. Please try again.");
                            }
                        } else {
                            System.out.println("User not found. Please sign up.");
                        }
                        break;
                    case 2:
                        System.out.print("Create a username: ");
                        String newUser = scanner.nextLine();
                        if (users.containsKey(newUser)) {
                            System.out.println("Username already exists. Please try a different username.");
                        } else {
                            System.out.print("Create a password: ");
                            String newPassword = scanner.nextLine();
                            users.put(newUser, new User(newUser, newPassword));
                            currentUser = newUser;
                            System.out.println("Sign up successful. Welcome, " + currentUser + "!");
                        }
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("\nWelcome, " + currentUser + "! What would you like to do?");
                System.out.println("1. Display available books");
                System.out.println("2. Search for books");
                System.out.println("3. Borrow a book");
                System.out.println("4. Return a book");
                System.out.println("5. Add a book");
                System.out.println("6. Remove a book");
                System.out.println("7. Calculate Fine");
                System.out.println("8. Search Books by Category");
                System.out.println("9. Log out");
                System.out.println("10. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.print("\n");

                switch (choice) {
                    case 1:
                        library.displayBooks();
                        break;
                    case 2:
                        System.out.print("Enter a keyword to search for: ");
                        String keyword = scanner.nextLine();
                        ArrayList<Book> searchResults = library.searchBooks(keyword);
                        if (searchResults.isEmpty()) {
                            System.out.println("No books found.");
                        } else {
                            System.out.println("Search Results:");
                            for (int i = 0; i < searchResults.size(); i++) {
                                Book book = searchResults.get(i);
                                System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor());
                            }
                        }
                        break;
                    case 3:
                        library.displayBooks();
                        System.out.print("Enter the index of the book to borrow: ");
                        int borrowIndex = scanner.nextInt();
                        scanner.nextLine();
                        library.borrowBook(borrowIndex - 1);
                        break;
                    case 4:
                        System.out.print("Enter the index of the book to return: ");
                        int returnIndex = scanner.nextInt();
                        scanner.nextLine();
                        library.returnBook(returnIndex - 1);
                        break;
                    case 5:
                        System.out.print("Enter the title of the book: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter the author of the book: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter the category of the book: ");
                        String category = scanner.nextLine();
                        library.addBook(new Book(title, author, category));
                        System.out.println("Book added successfully.");
                        break;
                    case 6:
                        library.displayBooks();
                        System.out.print("Enter the index of the book to remove: ");
                        int removeIndex = scanner.nextInt();
                        scanner.nextLine();
                        library.removeBook(removeIndex - 1);
                        break;
                    case 7:
                        System.out.print("Enter the index of the book: ");
                        int fine = scanner.nextInt();
                        scanner.nextLine();
                        library.calculateFine(fine);
                        break;
                    case 8:
                        System.out.print("Enter the category: ");
                        String categoryChoice = scanner.nextLine();
                        ArrayList<Book> booksByCategory = library.searchBooksByCategory(categoryChoice);
                        if (booksByCategory.isEmpty()) {
                            System.out.println("No books found in the category.");
                        } else {
                            System.out.println("Books in the " + categoryChoice + " category:");
                            for (int i = 0; i < booksByCategory.size(); i++) {
                                Book book = booksByCategory.get(i);
                                System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor());
                            }
                        }
                        break;
                    case 9:
                        currentUser = "";
                        System.out.println("Logged out successfully.");
                        break;
                    case 10:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
        scanner.close();
    }
}
