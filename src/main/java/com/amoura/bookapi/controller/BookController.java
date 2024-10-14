package com.amoura.bookapi.controller;

import com.amoura.bookapi.dto.BookInput;
import com.amoura.bookapi.exception.EntityNotFoundException;
import com.amoura.bookapi.exception.EntityUpdateException;
import com.amoura.bookapi.model.Book;
import com.amoura.bookapi.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

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
        // Gestion des exceptions: Si le livre n'est pas trouvé, on lance une EntityNotFoundException
        return bookService.getBookById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found"));
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
        try {
            return bookService.updateBook(id, book);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        } catch (Exception e) {
            throw new EntityUpdateException("Failed to update book with id: " + id);
        }
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        Optional<Book> existingBook = bookService.getBookById(id);

        // Gestion des exceptions: Si le livre à supprimer n'est pas trouvé, on lance une EntityNotFoundException
        if (existingBook.isEmpty()) {
            throw new EntityNotFoundException("Book with ID " + id + " not found for deletion");
        }

        bookService.deleteBook(id);
        return true;
    }
}
