package com.BookTracker.book_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Centralized exception handling component for the entire application.
 * <p>
 * This class uses {@link ControllerAdvice} to intercept exceptions thrown by any controller
 * and map them to specific user-friendly error views. It prevents raw stack traces
 * from being exposed to the user and ensures consistent error pages (404, 500, etc.).
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link BookNotFoundException} occurrences.
     * <p>
     * Instead of showing a generic error, this method catches the specific "not found" event,
     * adds the error message to the model, and renders the custom 404 view.
     * <p>
     * The {@code @ResponseStatus(HttpStatus.NOT_FOUND)} annotation ensures that the browser
     * receives the correct HTTP 404 status code, which is crucial for SEO and API clients.
     *
     * @param ex    The exception instance containing the "not found" details.
     * @param model The UI model to pass the error message to the view.
     * @return The name of the 404 error view template ("error/404").
     */
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBookNotFound(BookNotFoundException ex, Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "error/404";
    }

    /**
     * Fallback handler for any unexpected {@link Exception} (Internal Server Error).
     * <p>
     * This acts as a safety net for runtime errors (like NullPointerException, database connection failures, etc.)
     * that are not explicitly handled by other methods. It prevents the application from leaking
     * sensitive stack traces to the client.
     *
     * @param ex    The unexpected exception thrown during execution.
     * @param model The UI model to pass a generic user-friendly message.
     * @return The name of the generic error view template ("error/generic").
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Something unnexpected has happened. Try it again later");
        return "error/generic";
    }
}