package com.BookTracker.book_tracker.repository;

import com.BookTracker.book_tracker.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
