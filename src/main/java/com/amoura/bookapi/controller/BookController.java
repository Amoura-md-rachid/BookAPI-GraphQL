package com.amoura.bookapi.controller;

import com.amoura.bookapi.dto.BookInput;
import com.amoura.bookapi.model.Book;
import com.amoura.bookapi.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public List<Book> books() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookService.getBookById(id).orElse(null);
    }

    @QueryMapping
    public List<Book> findBooks(@Argument String title,
                                @Argument String author,
                                @Argument Integer publicationYear) {
        return bookService.findBooks(title, author, publicationYear);
    }

    @MutationMapping
    public Book createBook(@Argument BookInput bookInput) {
        Book book = new Book();
        book.setTitle(bookInput.getTitle());
        book.setAuthor(bookInput.getAuthor());
        book.setPublicationYear(bookInput.getPublicationYear());
        return bookService.createBook(book);
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument BookInput bookInput) {
        Book book = new Book();
        book.setTitle(bookInput.getTitle());
        book.setAuthor(bookInput.getAuthor());
        book.setPublicationYear(bookInput.getPublicationYear());
        return bookService.updateBook(id, book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        bookService.deleteBook(id);
        return true;
    }
}
