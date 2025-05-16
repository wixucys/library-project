package com.example;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
        library.addBook(new Book("1984", "George Orwell", "Dystopia"));
    }

    @Test
    void addBook_ShouldIncreaseBooksCount() {
        int initialSize = library.getAllBooks().size();
        library.addBook(new Book("Animal Farm", "George Orwell", "Satire"));
        assertEquals(initialSize + 1, library.getAllBooks().size());
    }

    @Test
    void removeBook_ExistingTitle_ShouldReturnTrue() {
        assertTrue(library.removeBook("1984"));
    }

    @Test
    void findByAuthor_ShouldReturnCorrectBooks() {
        List<Book> books = library.findByAuthor("George Orwell");
        assertFalse(books.isEmpty());
        assertEquals("1984", books.get(0).getTitle());
    }
}
