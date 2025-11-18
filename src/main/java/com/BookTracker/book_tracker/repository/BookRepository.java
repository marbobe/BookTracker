package com.BookTracker.book_tracker.repository;

import com.BookTracker.book_tracker.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b " +
            "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (:finishDate IS NULL OR b.finishDate <= :finishDate) " +
            "AND (:score IS NULL OR b.score >= :score)")
    Page<Book> findByFilters(
            @Param("title") String title,
            @Param("author") String author,
            @Param("finishDate") LocalDate finishDate,
            @Param("score") Integer score,
            Pageable pageable
    );
}
