package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String FILE_PATH = "library.txt";

    public void saveBooks(List<Book> books) throws IOException {
        // Создаем временный файл для безопасной записи
        File tempFile = new File(FILE_PATH + ".tmp");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Book book : books) {
                writer.write(escape(book.getTitle()) + ";" +
                        escape(book.getAuthor()) + ";" +
                        escape(book.getGenre()));
                writer.newLine();
            }
        }

        // Заменяем старый файл новым после успешной записи
        File oldFile = new File(FILE_PATH);
        if (oldFile.exists()) {
            oldFile.delete();
        }
        tempFile.renameTo(oldFile);
    }

    public List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 3); // Ограничиваем разбиение на 3 части
                if (parts.length == 3) {
                    books.add(new Book(
                            unescape(parts[0]),
                            unescape(parts[1]),
                            unescape(parts[2])
                    ));
                }
            }
        }
        return books;
    }

    private String escape(String str) {
        return str.replace(";", "\\;").replace("\n", "\\n");
    }

    private String unescape(String str) {
        return str.replace("\\;", ";").replace("\\n", "\n");
    }
}