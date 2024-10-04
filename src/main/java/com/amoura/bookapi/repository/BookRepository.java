package com.amoura.bookapi.repository;

import com.amoura.bookapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingAndAuthorContainingAndPublicationYear(
            String title, String author, Integer publicationYear);
}