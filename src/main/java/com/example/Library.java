package com.example;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();

    public boolean addBook(Book book) {
        if (books.stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle()))) {
            return false;
        }
        books.add(book);
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
