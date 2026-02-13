package com.BookTracker.book_tracker.repository;

import com.BookTracker.book_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Data Access Layer (Repository) for {@link User} entities.
 * <p>
 * This interface provides standard CRUD operations for user management and
 * includes custom retrieval methods needed for authentication and registration workflows.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user entity based on their unique username.
     * <p>
     * This method is automatically implemented by Spring Data JPA using the method name derivation.
     * It is critical for:
     * <ul>
     * <li><b>Authentication:</b> Loading the user to verify passwords during login.</li>
     * <li><b>Registration:</b> Checking if a username is already taken.</li>
     * </ul>
     *
     * @param username The username to search for (case-sensitive depending on DB collation).
     * @return An {@link Optional} containing the user if found, or empty if not found.
     */
    Optional<User> findByUsername(String username);
}
