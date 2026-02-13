package com.BookTracker.book_tracker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) used to capture and validate user registration details.
 * <p>
 * This class acts as a buffer between the client-side registration form and the internal
 * entity model. It ensures that raw input meets security standards (length, complexity)
 * before the system attempts to process the data or convert it into a persisted User.
 */
@Getter
@Setter
public class UserRegistrationDto {

    /**
     * The desired username for the new account.
     * <p>
     * <b>Validation:</b> Must not be null or consist solely of whitespace.
     */
    @NotBlank(message = "Username is required")
    private String username;


    /**
     * The raw, plain-text password provided by the user.
     * <p>
     * <b>Security Policy:</b>
     * <ul>
     * <li><b>Length:</b> Minimum 8 characters.</li>
     * <li><b>Complexity:</b> Must contain at least one numeric digit (0-9).</li>
     * </ul>
     * <p>
     * <b>Note:</b> This field is temporary. After validation, it must be hashed (e.g., via BCrypt)
     * before being stored in the database.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9]).*$", message = "Password must contain at least one number")
    private String password;
}
