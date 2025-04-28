package com.example;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();

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
        System.out.printf("Успех: Книга '%s' добавлена в библиотеку!%n", title);
        return true;
    }


    public boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equals(title));
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

}
