package com.example;

import java.util.List;
import java.util.Scanner;

public class Console {
    private final Library library;
    private final Scanner scanner;

    public Console() {
        this.library = new Library(new FileStorage("library.txt"));
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        printWelcomeMessage();
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            handleUserChoice(choice);
        }
    }

    private void printWelcomeMessage() {
        System.out.println("=== Библиотечная система v2.0 ===");
        System.out.println("Загружено книг: " + library.getAllBooks().size());
    }

    private void printMenu() {
        System.out.println("\nГлавное меню:");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Удалить книгу по названию");
        System.out.println("3. Показать все книги");
        System.out.println("4. Поиск по автору");
        System.out.println("5. Поиск по жанру");
        System.out.println("6. Сохранить и выйти");
        System.out.print("Введите номер операции: ");
    }

    private void handleUserChoice(String choice) {
        switch (choice) {
            case "1" -> addBookFlow();
            case "2" -> removeBookFlow();
            case "3" -> showAllBooks();
            case "4" -> searchByAuthorFlow();
            case "5" -> searchByGenreFlow();
            case "6" -> exitFlow();
            default -> System.out.println("⚠ Неверный ввод! Выберите 1-6");
        }
    }

    private void addBookFlow() {
        try {
            Book book = readBookFromInput();
            if (library.addBook(book)) {
                System.out.println("✅ Книга успешно добавлена!");
            } else {
                System.out.println("⚠ Книга уже существует в библиотеке");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
        }
    }

    private Book readBookFromInput() {
        System.out.println("\nВведите данные книги:");
        String title = readNonEmptyInput("Название");
        String author = readNonEmptyInput("Автор");
        String genre = readNonEmptyInput("Жанр");
        return new Book(title, author, genre);
    }

    private String readNonEmptyInput(String fieldName) {
        while (true) {
            System.out.print(fieldName + ": ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("⚠ Поле не может быть пустым!");
        }
    }

    private void removeBookFlow() {
        System.out.print("\nВведите название книги для удаления: ");
        String title = scanner.nextLine().trim();
        if (library.removeBook(title)) {
            System.out.println("✅ Книга успешно удалена");
        } else {
            System.out.println("⚠ Книга с таким названием не найдена");
        }
    }

    private void showAllBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("\nБиблиотека пуста");
            return;
        }

        System.out.println("\nВсе книги (" + books.size() + "):");
        System.out.println("----------------------------------------");
        books.forEach(book -> System.out.printf(
                "▸ %-25s | %-20s | %-15s%n",
                book.getTitle(),
                book.getAuthor(),
                book.getGenre()
        ));
    }

    private void searchByAuthorFlow() {
        System.out.print("\nВведите автора для поиска: ");
        String author = scanner.nextLine().trim();
        List<Book> results = library.findByAuthor(author);
        printSearchResults(results, "автору '" + author + "'");
    }

    private void searchByGenreFlow() {
        System.out.print("\nВведите жанр для поиска: ");
        String genre = scanner.nextLine().trim();
        List<Book> results = library.findByGenre(genre);
        printSearchResults(results, "жанру '" + genre + "'");
    }

    private void printSearchResults(List<Book> results, String searchCriteria) {
        if (results.isEmpty()) {
            System.out.println("\nПо " + searchCriteria + " ничего не найдено");
            return;
        }

        System.out.println("\nРезультаты поиска (" + results.size() + " по " + searchCriteria + "):");
        System.out.println("----------------------------------------");
        results.forEach(book -> System.out.printf(
                "▸ %-25s | %-15s%n",
                book.getTitle(),
                book.getGenre()
        ));
    }

    private void exitFlow() {
        try {
            library.saveToFile();
            System.out.println("\n✅ Данные успешно сохранены");
            System.out.println("До свидания!");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("\n❌ Ошибка при сохранении данных: " + e.getMessage());
            System.out.println("Данные могут быть потеряны!");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Console().run();
    }
}