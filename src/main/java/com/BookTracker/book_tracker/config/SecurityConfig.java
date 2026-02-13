package com.BookTracker.book_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Main security configuration for the BookTracker application.
 * <p>
 * This class defines the security filter chain, specifying which URL paths are public
 * and which require authentication. It also configures the custom login form behavior
 * and the password encoding strategy.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the HTTP security filter chain.
     * <p>
     * This method defines the access rules for the application:
     * <ul>
     * <li>Permits public access to static resources (CSS, JS, images) and auth endpoints.</li>
     * <li>Requires authentication for all other application views.</li>
     * <li>Sets up the custom form-based authentication flow.</li>
     * </ul>
     *
     * @param http The HttpSecurity object to configure.
     * @return The built SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Allow unrestricted access to public pages, auth endpoints, and static resources
                        .requestMatchers("/","/login","/register", "/login/guest", "/css/**", "/js/**","/favicon.ico", "/images/**").permitAll()
                        // Enforce authentication for all other requests (e.g., /index, /books/**)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Specifies the custom login page location
                        .defaultSuccessUrl("/index", true) // Redirects here after a successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // Redirects here after logout
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Provides the password encoder bean used for hashing and verifying passwords.
     * <p>
     * Uses BCrypt, a strong hashing function designed to be slow to resist brute-force attacks.
     *
     * @return A new instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
