package com.BookTracker.book_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling the application's landing page.
 * <p>
 * This class serves as the entry point for users visiting the root domain,
 * directing them to the welcome screen or public home view.
 */
@Controller
public class HomeController {

    /**
     * Serves the home page of the application.
     * <p>
     * Maps the root URL ("/") to the "home" view template.
     * This method is triggered when a user accesses the main domain of the application.
     *
     * @return The logical name of the view ("home") to be rendered by the template engine.
     */
    @GetMapping("/")
    public String index() {
        return "home";
    }
}
