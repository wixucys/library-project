package com.example;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final Library library;
    private final FileStorage storage;
    private final Scanner scanner;

    public Console() {
        this.library = new Library();
        this.storage = new FileStorage();
        this.scanner = new Scanner(System.in);
        loadInitialData();
    }

    private void loadInitialData() {
        try {
            List<Book> books = storage.loadBooks();
            library.bulkAddBooks(books);
            System.out.println("Данные загружены! Загружено книг: " + books.size());
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

        boolean result = library.addBook(title, author, genre);
        // Сообщения уже выводятся в методе addBook класса Library
        // Здесь мы просто получаем результат операции
    }

    private void removeBook() {
        System.out.print("Название книги для удаления: ");
        String title = scanner.nextLine();

        if (library.removeBook(title)) {
            System.out.println("Книга успешно удалена!");
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
            System.out.println("Данные успешно сохранены в файл " + FileStorage.FILE_PATH);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
            System.err.println("Попробуйте проверить права доступа к файлу " +
                    new File(FileStorage.FILE_PATH).getAbsolutePath());
        }
        System.exit(0);
    }
}