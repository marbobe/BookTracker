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

/**
 * Data Access Layer (Repository) for {@link Book} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations
 * and defines custom JPQL queries for complex filtering and sequence calculation.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Executes a dynamic search query with multiple optional filters.
     * <p>
     * The query logic handles {@code NULL} parameters gracefully: if a parameter is null,
     * that specific filter condition is ignored (acts as a wildcard).
     * <p>
     * <b>Filter Logic:</b>
     * <ul>
     * <li><b>User:</b> Mandatory. Restricts results to the authenticated user.</li>
     * <li><b>Title/Author:</b> Case-insensitive partial match (LIKE %value%).</li>
     * <li><b>Finish Date:</b> Returns books finished on or before the provided date.</li>
     * <li><b>Score:</b> Exact match.</li>
     * </ul>
     *
     * @param title      The partial title to search for (can be null).
     * @param author     The partial author name to search for (can be null).
     * @param finishDate The maximum finish date (can be null).
     * @param score      The specific rating to filter by (can be null).
     * @param user       The owner of the books (cannot be null).
     * @param pageable   Pagination information (page number, size, sorting).
     * @return A {@link Page} of books matching the criteria.
     */
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

    /**
     * Calculates the chronological sequence number of a book relative to the user's collection.
     * <p>
     * Since the database ID is global and non-sequential per user (e.g., a user might have IDs 1, 5, 12),
     * this method calculates a "virtual index" (1, 2, 3...) based on creation time.
     * <p>
     * <b>Logic:</b> It counts how many active books belong to the user that were created <i>before</i>
     * the target book. If creation times are identical, the ID is used as a tie-breaker.
     *
     * @param date The creation timestamp of the current book.
     * @param id   The ID of the current book (for tie-breaking).
     * @param user The owner of the collection.
     * @return The calculated position (1-based index) of the book in the user's timeline.
     */
    @Query("SELECT COUNT(b) + 1 FROM Book b WHERE (b.createdAt < :date OR (b.createdAt = :date AND b.id < :id)) AND b.active = true AND b.user = :user")
    Long getDisplaySequenceNumber(
            @Param("date")LocalDateTime date,
            @Param("id") Long id,
            @Param("user") User user
            );
}
