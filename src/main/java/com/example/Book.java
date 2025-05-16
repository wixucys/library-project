package com.example;

import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final String genre;

    public Book(String title, String author, String genre) {
        this.title = normalizeString(title);
        this.author = normalizeString(author);
        this.genre = normalizeString(genre);

        validateFields();
    }

    private void validateFields() {
        if (title.isBlank() || author.isBlank() || genre.isBlank()) {
            throw new IllegalArgumentException("Все поля книги должны быть заполнены");
        }
    }

    private static String normalizeString(String str) {
        return str != null ? str.trim().toLowerCase() : "";
    }

    public boolean equalsIgnoreCase(Book other) {
        return this.title.equals(other.title) &&
                this.author.equals(other.author);
    }

    public boolean matchesTitle(String title) {
        return this.title.equals(normalizeString(title));
    }

    public boolean matchesAuthor(String author) {
        return this.author.equals(normalizeString(author));
    }

    public boolean matchesGenre(String genre) {
        return this.genre.equals(normalizeString(genre));
    }


    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title) &&
                author.equals(book.author) &&
                genre.equals(book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre);
    }
}