package com.BookTracker.book_tracker.repository;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b " +
            "WHERE (:user = b.user) " +
            "AND (CAST(:title AS string) IS NULL OR LOWER(CAST(b.title AS string)) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%'))) " +
            "AND (CAST(:author AS string) IS NULL OR LOWER(CAST(b.author AS string)) LIKE LOWER(CONCAT('%', CAST(:author AS string), '%'))) " +
            "AND (CAST(:finishDate AS localdate) IS NULL OR b.finishDate <= :finishDate) " +
            "AND (CAST(:score AS integer) IS NULL OR b.score = :score)")
    Page<Book> findByFilters(
            @Param("title") String title,
            @Param("author") String author,
            @Param("finishDate") LocalDate finishDate,
            @Param("score") Integer score,
            @Param("user") User user,
            Pageable pageable
    );

    @Query("SELECT COUNT(b) + 1 FROM Book b WHERE (b.createdAt < :date OR (b.createdAt = :date AND b.id < :id)) AND b.active = true AND b.user = :user")
    Long getDisplaySequenceNumber(
            @Param("date")LocalDateTime date,
            @Param("id") Long id,
            @Param("user") User user
            );
}
