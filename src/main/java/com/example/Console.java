package com.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Console {
    private Library library;
    private FileStorage storage;
    private Scanner scanner;

    public Console() {
        this.library = new Library();
        this.storage = new FileStorage();
        this.scanner = new Scanner(System.in);
        loadData();
    }


    private void loadData() {
        try {
            List<Book> books = storage.loadBooks();
            books.forEach(library::addBook);
            System.out.println("Данные загружены!");
        } catch (IOException e) {
            System.err.println("Ошибка загрузки: " + e.getMessage());
        }
    }


    public void run() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            handleUserChoice(choice);
        }
    }


    private void printMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Удалить книгу");
        System.out.println("3. Показать все книги");
        System.out.println("4. Поиск по автору");
        System.out.println("5. Поиск по жанру");
        System.out.println("6. Выход");
        System.out.print("Выберите действие: ");
    }


    private void handleUserChoice(String choice) {
        switch (choice) {
            case "1" -> addBook();
            case "2" -> removeBook();
            case "3" -> showAllBooks();
            case "4" -> searchByAuthor();
            case "5" -> searchByGenre();
            case "6" -> { saveAndExit(); System.exit(0); }
            default -> System.out.println("Неверный ввод!");
        }
    }


    private void addBook() {
        System.out.print("Название: ");
        String title = scanner.nextLine();
        System.out.print("Автор: ");
        String author = scanner.nextLine();
        System.out.print("Жанр: ");
        String genre = scanner.nextLine();

        Book book = new Book(title, author, genre);
        library.addBook(book);
        System.out.println("Книга добавлена!");
    }


    private void removeBook() {
        System.out.print("Название книги для удаления: ");
        String title = scanner.nextLine();

        if (library.removeBook(title)) {
            System.out.println("Книга удалена!");
        } else {
            System.out.println("Книга не найдена!");
        }
    }


    private void showAllBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Библиотека пуста!");
            return;
        }

        System.out.println("\nСписок книг:");
        books.forEach(book -> System.out.println(
                book.getTitle() + " | " + book.getAuthor() + " | " + book.getGenre()
        ));
    }


    private void searchByAuthor() {
        System.out.print("Автор: ");
        String author = scanner.nextLine();
        List<Book> books = library.findByAuthor(author);

        if (books.isEmpty()) {
            System.out.println("Книги не найдены!");
        } else {
            System.out.println("\nНайденные книги:");
            books.forEach(book -> System.out.println(book.getTitle() + " (" + book.getGenre() + ")"));
        }
    }


    private void searchByGenre() {
        System.out.print("Жанр: ");
        String genre = scanner.nextLine();
        List<Book> books = library.findByGenre(genre);

        if (books.isEmpty()) {
            System.out.println("Книги не найдены!");
        } else {
            System.out.println("\nНайденные книги:");
            books.forEach(book -> System.out.println(book.getTitle() + " (" + book.getAuthor() + ")"));
        }
    }


    private void saveAndExit() {
        try {
            storage.saveBooks(library.getAllBooks());
            System.out.println("Данные сохранены. До свидания!");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
        }
    }
}
