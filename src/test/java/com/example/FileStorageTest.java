package com.example;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {
    private FileStorage storage;
    private List<Book> testBooks;

    @BeforeEach
    void setUp() {
        storage = new FileStorage();
        testBooks = List.of(
                new Book("1984", "George Orwell", "Dystopia"),
                new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy")
        );
    }

    @Test
    void saveAndLoadBooks_ShouldPreserveData() throws IOException, ClassNotFoundException {
        storage.saveBooks(testBooks);
        List<Book> loadedBooks = storage.loadBooks();
        assertEquals(testBooks.size(), loadedBooks.size());
        assertEquals("1984", loadedBooks.get(0).getTitle());
    }
}
