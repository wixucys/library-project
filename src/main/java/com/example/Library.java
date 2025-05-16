package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();
    private FileStorage fileStorage = new FileStorage();

    public Library(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
        try {
            this.books = fileStorage.loadBooks();
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке книг: " + e.getMessage());
            this.books = new ArrayList<>();
        }
    }

    public void addBooks(List<Book> booksToAdd) {
        if (booksToAdd == null) return;

        booksToAdd.stream()
                .filter(this::isValidBook)
                .filter(book -> !bookExists(book))
                .forEach(books::add);

        saveToFile();
    }

    public boolean addBook(Book book) {
        if (!isValidBook(book) || bookExists(book)) {
            return false;
        }

        books.add(book);
        saveToFile();
        return true;
    }

    public boolean removeBook(String title) {
        boolean removed = books.removeIf(book -> book.matchesTitle(title));
        if (removed) saveToFile();
        return removed;
    }

    private boolean bookExists(Book book) {
        return books.stream().anyMatch(b -> b.equalsIgnoreCase(book));
    }

    private boolean isValidBook(Book book) {
        return book != null &&
                !book.getTitle().isBlank() &&
                !book.getAuthor().isBlank() &&
                !book.getGenre().isBlank();
    }

    public List<Book> findByAuthor(String author) {
        return books.stream()
                .filter(book -> book.matchesAuthor(author))
                .toList();
    }

    public List<Book> findByGenre(String genre) {
        return books.stream()
                .filter(book -> book.matchesGenre(genre))
                .toList();
    }

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

    public void saveToFile() {
        try {
            fileStorage.saveBooks(books);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
        }
    }
}