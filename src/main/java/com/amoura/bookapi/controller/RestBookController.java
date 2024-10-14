package com.amoura.bookapi.controller;

import com.amoura.bookapi.dto.BookInput;
import com.amoura.bookapi.exception.ResourceNotFoundException;
import com.amoura.bookapi.model.Book;
import com.amoura.bookapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class RestBookController {
    private final BookService bookService;

    public RestBookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // GET a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // GET books by title, author, and publication year
    @GetMapping("/search")
    public ResponseEntity<List<Book>> findBooks(@RequestParam(required = false) String title,
                                                @RequestParam(required = false) String author,
                                                @RequestParam(required = false) Integer publicationYear) {
        List<Book> books = bookService.findBooks(title, author, publicationYear);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // POST to create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookInput bookInput) {
        Book book = new Book();
        book.setTitle(bookInput.getTitle());
        book.setAuthor(bookInput.getAuthor());
        book.setPublicationYear(bookInput.getPublicationYear());
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // PUT to update an existing book by ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookInput bookInput) {
        Book existingBook = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
        existingBook.setTitle(bookInput.getTitle());
        existingBook.setAuthor(bookInput.getAuthor());
        existingBook.setPublicationYear(bookInput.getPublicationYear());
        Book updatedBook = bookService.updateBook(id, existingBook);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // DELETE a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
