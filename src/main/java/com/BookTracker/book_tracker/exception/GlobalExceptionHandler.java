package com.BookTracker.book_tracker.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFound(BookNotFoundException ex, Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "error/404";
    }

    // Un "comodín" para cualquier otro error inesperado (Error 500)
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Something unnexpected has happened. Try it again later");
        return "error/generic";
    }
}