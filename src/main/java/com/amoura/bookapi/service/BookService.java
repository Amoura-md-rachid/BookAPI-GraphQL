package com.amoura.bookapi.service;

import com.amoura.bookapi.model.Book;
import com.amoura.bookapi.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        List<Book> books = bookRepository.findAll();
        logger.info("Found {} books", books.size());
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            logger.info("Book found: {}", book.get());
        } else {
            logger.warn("Book with ID: {} not found", id);
        }
        return book;
    }

    public Book createBook(Book book) {
        logger.info("Creating new book: {}", book);
        Book createdBook = bookRepository.save(book);
        logger.info("Book created with ID: {}", createdBook.getId());
        return createdBook;
    }

    public Book updateBook(Long id, Book bookDetails) {
        logger.info("Updating book with ID: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book with ID: {} not found", id);
                    return new RuntimeException("Book not found");
                });
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublicationYear(bookDetails.getPublicationYear());
        Book updatedBook = bookRepository.save(book);
        logger.info("Book with ID: {} updated successfully", id);
        return updatedBook;
    }

    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
        logger.info("Book with ID: {} deleted successfully", id);
    }

    public List<Book> findBooks(String title, String author, Integer publicationYear) {
        logger.info("Finding books with title: {}, author: {}, publication year: {}",
                title != null ? title : "any",
                author != null ? author : "any",
                publicationYear != null ? publicationYear : "any");

        List<Book> books = bookRepository.findByTitleContainingAndAuthorContainingAndPublicationYear(
                title != null ? title : "",
                author != null ? author : "",
                publicationYear
        );

        logger.info("Found {} books matching the criteria", books.size());
        return books;
    }
}
