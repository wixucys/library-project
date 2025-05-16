package com.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private final String filePath;

    public FileStorage(String filePath) {
        this.filePath = filePath;
    }

    public void saveBooks(List<Book> books) throws IOException {
        File tempFile = File.createTempFile("library", ".tmp");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Book book : books) {
                writer.write(escape(book.getTitle()) + ";" +
                        escape(book.getAuthor()) + ";" +
                        escape(book.getGenre()));
                writer.newLine();
            }
        }

        Files.move(tempFile.toPath(), new File(filePath).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
    }

    public List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = unescape(line).split(";", 3);
                if (parts.length == 3) {
                    books.add(new Book(parts[0], parts[1], parts[2]));
                }
            }
        }
        return books;
    }

    private String escape(String str) {
        return str.replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace("\n", "\\n");
    }

    private String unescape(String line) {
        return line.replace("\\;", ";")
                .replace("\\n", "\n")
                .replace("\\\\", "\\");
    }
}