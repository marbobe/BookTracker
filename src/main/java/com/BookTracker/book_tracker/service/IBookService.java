package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface defining the business logic contract for Book management.
 * <p>
 * This interface decouples the controller layer from the implementation details,
 * ensuring that the web layer only interacts with abstract operations (CRUD, filtering, calculation).
 */
public interface IBookService {

    /**
     * Retrieves a paginated list of all books in the system.
     *
     * @param pageable The pagination information (page, size, sort).
     * @return A page of {@link Book} entities.
     */
    public Page<Book> getBooks(Pageable pageable);

    /**
     * Retrieves all books without pagination.
     * <p>
     * <b>Performance Warning:</b> This method loads the entire dataset into memory.
     * Use with caution or only for small datasets/exports.
     *
     * @return A list containing every book in the database.
     */
    public List<Book> findAll();

    /**
     * Finds a specific book by its unique ID.
     *
     * @param id The unique identifier of the book.
     * @return The found {@link Book} entity.
     * @throws com.BookTracker.book_tracker.exception.BookNotFoundException if the book does not exist.
     */
    public Book findById(Long id);

    /**
     * Persists a book entity (creates new or updates existing).
     *
     * @param book The book object to save.
     */
    public void save(Book book);

    /**
     * Deletes a book by its unique ID.
     *
     * @param id The identifier of the book to remove.
     */
    public void deleteById(Long id);

    /**
     * Calculates the chronological sequence number (index) of a book within a user's collection.
     * <p>
     * This is used to display "Book #1", "Book #2" in the UI based on creation date,
     * independent of the database primary key.
     *
     * @param date The creation timestamp of the book.
     * @param id   The ID of the book (used as a tie-breaker).
     * @param user The owner of the collection.
     * @return The calculated 1-based index.
     */
    public Long getSequenceNumber(LocalDateTime date, Long id, User user);

    /**
     * Retrieves a paginated list of books based on dynamic search criteria for a specific user.
     *
     * @param title      The partial title to search for (can be null).
     * @param author     The partial author name to search for (can be null).
     * @param finishDate The maximum finish date (can be null).
     * @param score      The specific score (can be null).
     * @param user       The authenticated user who owns the books.
     * @param pageable   Pagination and sorting configuration.
     * @return A page of filtered books.
     */
    Page<Book> findByFilters(String title, String author, LocalDate finishDate, Integer score, User user, Pageable pageable);
}
