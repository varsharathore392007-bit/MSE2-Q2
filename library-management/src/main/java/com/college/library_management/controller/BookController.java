
package com.college.library_management.controller;

import com.college.library_management.model.Book;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // Store books in memory
    private List<Book> books = new ArrayList<>();

    // Generate unique IDs
    private AtomicInteger idCounter = new AtomicInteger(1);

    // 1. Add a new book (POST)
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        book.setId(idCounter.getAndIncrement());
        books.add(book);

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // 2. Get all books (GET)
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    // 3. Get book by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {

        for (Book book : books) {
            if (book.getId().equals(id)) {
                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // 4. Update book details (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id,
            @RequestBody Book updatedBook) {

        for (Book book : books) {

            if (book.getId().equals(id)) {

                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());
                book.setAvailableCopies(
                    updatedBook.getAvailableCopies()
                );

                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // 5. Delete book (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {

        boolean removed = books.removeIf(
            book -> book.getId().equals(id)
        );

        if (removed) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
