package com.amoura.bookapi.controller;

import com.amoura.bookapi.dto.BookInput;
import com.amoura.bookapi.model.Book;
import com.amoura.bookapi.service.BookService;
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
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // GET a book by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id).orElse(null);
    }

    // GET books by title, author, and publication year
    @GetMapping("/search")
    public List<Book> findBooks(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String author,
                                @RequestParam(required = false) Integer publicationYear) {
        return bookService.findBooks(title, author, publicationYear);
    }

    // POST to create a new book
    @PostMapping
    public Book createBook(@RequestBody BookInput bookInput) {
        Book book = new Book();
        book.setTitle(bookInput.getTitle());
        book.setAuthor(bookInput.getAuthor());
        book.setPublicationYear(bookInput.getPublicationYear());
        return bookService.createBook(book);
    }

    // PUT to update an existing book by ID
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody BookInput bookInput) {
        Book book = new Book();
        book.setTitle(bookInput.getTitle());
        book.setAuthor(bookInput.getAuthor());
        book.setPublicationYear(bookInput.getPublicationYear());
        return bookService.updateBook(id, book);
    }

    // DELETE a book by ID
    @DeleteMapping("/{id}")
    public Boolean deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return true;
    }
}
