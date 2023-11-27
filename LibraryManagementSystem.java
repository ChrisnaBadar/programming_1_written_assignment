import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private int quantity;

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int delta) {
        quantity += delta;
    }
}

class Library {
    private Map<String, Book> bookInventory = new HashMap<>();
    private Map<String, Integer> borrowedBooks = new HashMap<>();

    public void addBook(String title, String author, int quantity) {
        Book existingBook = bookInventory.get(title);
        if (existingBook != null) {
            // Book already exists, update quantity
            existingBook.updateQuantity(quantity);
        } else {
            // Add new book to the library
            bookInventory.put(title, new Book(title, author, quantity));
        }
    }

    public void displayBooks() {
        System.out.println("Books in the library:");
        for (Book book : bookInventory.values()) {
            System.out.println("Title: " + book.getTitle() +
                               ", Author: " + book.getAuthor() +
                               ", Quantity: " + book.getQuantity());
        }
    }

    public void displayBorrowedBooks() {
        System.out.println("Borrowed Books:");
        for (Map.Entry<String, Integer> entry : borrowedBooks.entrySet()) {
            System.out.println("Title: " + entry.getKey() +
                               ", Quantity Borrowed: " + entry.getValue());
        }
    }

    public void borrowBook(String title, int quantity) {
        Book book = bookInventory.get(title);
        try {
            if (book != null && book.getQuantity() >= quantity) {
                book.updateQuantity(-quantity);
                borrowedBooks.put(title, borrowedBooks.getOrDefault(title, 0) + quantity);
                System.out.println("Books borrowed successfully.");
            } else {
                System.out.println("Sorry, requested books not available.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for quantity. Please enter a valid number.");
        }
    }

    public void returnBook(String title, int quantity) {
        Book book = bookInventory.get(title);
        try {
            if (book != null && borrowedBooks.containsKey(title) && borrowedBooks.get(title) >= quantity) {
                book.updateQuantity(quantity);
                borrowedBooks.put(title, borrowedBooks.get(title) - quantity);
                System.out.println("Books returned successfully.");
            } else {
                System.out.println("Error: Book not found in the library system or invalid quantity.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for quantity. Please enter a valid number.");
        }
    }

    public void initializeLibrary() {
        // Add 5 random books to the library as a starter
        addBook("The Great Gatsby", "F. Scott Fitzgerald", 10);
        addBook("To Kill a Mockingbird", "Harper Lee", 8);
        addBook("1984", "George Orwell", 12);
        addBook("The Catcher in the Rye", "J.D. Salinger", 6);
        addBook("Pride and Prejudice", "Jane Austen", 15);
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        library.initializeLibrary();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            try {
                
                System.out.println("\nLibrary Management System");
                System.out.println("1. Add Books");
                System.out.println("2. Borrow Books");
                System.out.println("3. Return Books");
                System.out.println("4. Exit");

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        library.addBook(title, author, quantity);
                        break;

                    case 2: // Borrow Books
                        library.displayBooks();
                        System.out.print("Enter book title: ");
                        String borrowTitle = scanner.nextLine();

                        try {
                            System.out.print("Enter quantity to borrow: ");
                            int borrowQuantity = scanner.nextInt();
                            scanner.nextLine(); // consume the newline character
                            library.borrowBook(borrowTitle, borrowQuantity);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input for quantity. Please enter a valid number.");
                            scanner.nextLine(); // clear the input buffer
                        }
                        break;

                    case 3:
                        library.displayBorrowedBooks();
                        System.out.print("Enter book title: ");
                        String returnTitle = scanner.nextLine();

                        try {
                            System.out.print("Enter quantity to return: ");
                            int returnQuantity = scanner.nextInt();
                            scanner.nextLine(); // consume the newline character
                            library.returnBook(returnTitle, returnQuantity);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input for quantity. Please enter a valid number.");
                            scanner.nextLine(); // clear the input buffer
                        }
                        break;

                    case 4:
                        System.out.println("Exiting Library Management System. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the input buffer
                choice = 0;
            }
        } while (choice != 4);

        scanner.close();
    }
}
