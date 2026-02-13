package com.BookTracker.book_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested book entry cannot be found in the database.
 * <p>
 * This exception is typically triggered by service methods when a lookup by ID fails.
 * The {@code @ResponseStatus} annotation ensures that Spring Boot automatically returns
 * an HTTP 404 (Not Found) status when this exception is thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    /**
     * Constructs a new BookNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining which resource was not found (e.g., "Book with ID 5 not found").
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
