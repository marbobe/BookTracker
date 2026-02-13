package com.BookTracker.book_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Book Tracker application.
 * <p>
 * This class bootstraps the Spring Boot application, initializing the Spring context,
 * component scanning, and auto-configuration mechanisms.
 */
@SpringBootApplication
public class BookTrackerApplication {

	/**
	 * The main method that launches the application.
	 *
	 * @param args Command-line arguments passed during startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookTrackerApplication.class, args);
	}

}
