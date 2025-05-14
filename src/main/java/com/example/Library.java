package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();
    private FileStorage fileStorage = new FileStorage();

    public Library() {
        try {
            books = fileStorage.loadBooks();
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке книг из файла: " + e.getMessage());
        }
    }

    public void bulkAddBooks(List<Book> booksToAdd) {
        if (booksToAdd != null) {
            books.addAll(booksToAdd);
            saveToFile();
        }
    }

    public boolean bookExists(String title, String author) {
        String normalizedTitle = title.trim().toLowerCase();
        String normalizedAuthor = author.trim().toLowerCase();

        return books.stream().anyMatch(book ->
                book.getTitle().trim().toLowerCase().equals(normalizedTitle) &&
                        book.getAuthor().trim().toLowerCase().equals(normalizedAuthor)
        );
    }

    public boolean addBook(String title, String author, String genre) {
        if (title == null || author == null || genre == null ||
                title.isBlank() || author.isBlank() || genre.isBlank()) {
            System.out.println("Ошибка: Все поля книги должны быть заполнены!");
            return false;
        }

        if (bookExists(title, author)) {
            System.out.printf("Ошибка: Книга '%s' автора %s уже существует!%n", title, author);
            return false;
        }

        books.add(new Book(title, author, genre));
        saveToFile();
        System.out.printf("Успех: Книга '%s' добавлена в библиотеку!%n", title);
        return true;
    }

    public boolean addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getAuthor() == null || book.getGenre() == null ||
                book.getTitle().isBlank() || book.getAuthor().isBlank() || book.getGenre().isBlank()) {
            System.out.println("Ошибка: Книга или её поля не могут быть пустыми!");
            return false;
        }

        if (bookExists(book.getTitle(), book.getAuthor())) {
            System.out.printf("Ошибка: Книга '%s' автора %s уже существует!%n", book.getTitle(), book.getAuthor());
            return false;
        }

        books.add(book);
        saveToFile();
        System.out.printf("Успех: Книга '%s' добавлена в библиотеку!%n", book.getTitle());
        return true;
    }

    public boolean removeBook(String title) {
        boolean removed = books.removeIf(book -> book.getTitle().equals(title));
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    public List<Book> findByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equals(author))
                .toList();
    }

    public List<Book> findByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equals(genre))
                .toList();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    private void saveToFile() {
        try {
            fileStorage.saveBooks(books);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении книг в файл: " + e.getMessage());
        }
    }
}