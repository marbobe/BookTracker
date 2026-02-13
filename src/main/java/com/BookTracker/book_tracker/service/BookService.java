package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.exception.BookNotFoundException;
import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.model.User;
import com.BookTracker.book_tracker.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service Implementation for managing {@link Book} entities.
 * <p>
 * This class encapsulates the business logic for the application, acting as a bridge
 * between the controller layer and the persistence layer (Repository).
 * It handles exception translation (e.g., throwing {@link BookNotFoundException})
 * and post-processing of data (e.g., calculating display indices).
 */
@Service
public class BookService implements IBookService{

    private final BookRepository repository;

    /**
     * Dependency injection constructor.
     *
     * @param repository The data access component for books.
     */
    public BookService(BookRepository repository){
        this.repository = repository;
    }

    /**
     * Retrieves a paginated list of all books in the database.
     *
     * @param pageable The pagination information (page number, size, sort).
     * @return A page of book entities.
     */
    public Page<Book> getBooks(Pageable pageable){
        return this.repository.findAll(pageable);
    }

    /**
     * Retrieves all books without pagination.
     * <p>
     * <b>Warning:</b> Use with caution on large datasets as it loads all records into memory.
     *
     * @return A complete list of books.
     */
    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a specific book by its unique ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The found Book entity.
     * @throws BookNotFoundException if no book exists with the given ID.
     */
    @Override
    public Book findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException("The book with ID " + id + " doesn't exists in our library"));
    }

    /**
     * Persists a book entity (creates new or updates existing).
     *
     * @param book The book entity to save.
     */
    @Override
    public void save(Book book) {
        repository.save(book);
    }

    /**
     * Deletes a book by its ID.
     * <p>
     * Note: Depending on the entity configuration, this might perform a soft delete
     * or a hard delete in the database.
     *
     * @param id The ID of the book to remove.
     */
    @Override
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    /**
     * Calculates the chronological position of a book for a specific user.
     *
     * @param date The creation date of the reference book.
     * @param id   The ID of the reference book (tie-breaker).
     * @param user The owner of the collection.
     * @return The 1-based index (e.g., 1st book, 2nd book...).
     */
    @Override
    public Long getSequenceNumber(LocalDateTime date, Long id, User user) {
        return repository.getDisplaySequenceNumber(date, id, user);
    }


    /**
     * Retrieves a paginated list of books based on dynamic filters and calculates
     * a transient display index for each result.
     * <p>
     * <b>Logic:</b>
     * <ol>
     * <li>Fetches the page of books matching the criteria from the database.</li>
     * <li>Iterates through the results to calculate the {@code displayIndex} for each book.
     * This index represents the chronological order of the book in the user's personal timeline.</li>
     * </ol>
     *
     * @param title      Filter by partial title.
     * @param author     Filter by partial author name.
     * @param finishDate Filter by finish date (on or before).
     * @param score      Filter by exact score.
     * @param user       The authenticated user (mandatory).
     * @param pageable   Pagination details.
     * @return A Page of books enriched with their display index.
     */
    @Override
    public Page<Book> findByFilters(String title, String author, LocalDate finishDate, Integer score, User user, Pageable pageable) {
        Page<Book> booksPage = repository.findByFilters(title, author, finishDate, score, user, pageable);

        for (Book book : booksPage.getContent()){
            Long globalIndex = repository.getDisplaySequenceNumber(
                    book.getCreatedAt(),
                    book.getId(),
                    user
            );
            book.setDisplayIndex(globalIndex);
        }
        return booksPage;
    }
}
