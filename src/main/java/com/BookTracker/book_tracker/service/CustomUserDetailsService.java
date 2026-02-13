package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Core service used by Spring Security to handle user authentication.
 * <p>
 * This class implements the standard {@link UserDetailsService} interface to bridge
 * the application's database (via {@link UserRepository}) with the Spring Security framework.
 * When a user attempts to log in, the {@code DaoAuthenticationProvider} delegates the
 * user lookup to this service.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor injection
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username.
     * <p>
     * This method is automatically triggered by Spring Security during the authentication process.
     * It queries the database for the user's credentials and roles.
     *
     * @param username The username identifying the user whose data is required.
     * @return A fully populated {@link UserDetails} object (which is implemented by the {@code User} entity).
     * @throws UsernameNotFoundException if the user cannot be found in the database.
     * This exception tells Spring Security to fail the authentication attempt.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
