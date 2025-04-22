package com.example;

import java.io.*;
import java.util.*;
import java.util.List;

public class FileStorage {
    private static final String FILE_PATH = "library.txt";

    // Сохранить книги в текстовый файл
    public void saveBooks(List<Book> books) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                writer.write(book.getTitle() + ";" + book.getAuthor() + ";" + book.getGenre());
                writer.newLine();
            }
        }
    }

    // Загрузить книги из текстового файла
    public List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    books.add(new Book(parts[0], parts[1], parts[2]));
                }
            }
        }
        return books;
    }
}