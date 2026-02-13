package com.BookTracker.book_tracker.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents a registered user in the system and acts as the Security Principal.
 * <p>
 * This entity implements Spring Security's {@link UserDetails} interface, allowing it to be
 * used directly by the authentication manager. It maps to the "app_users" table and holds
 * both the account credentials and the relationship to the user's data (Books).
 */
@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Unique identifier for the user account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    /**
     * The unique login identifier.
     * <p>
     * Marked as {@code unique = true} to prevent duplicate account creation at the database level.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The encoded password (Bcrypt hash).
     * <p>
     * <b>Security Warning:</b> This field must never store plain-text passwords.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The collection of books tracked by this user.
     * <p>
     * Configured with {@code CascadeType.ALL}: If a user account is deleted,
     * all their associated books are automatically removed from the database (Orphan Removal).
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Book> books;

    // -------------------------------------------------------------------------
    // UserDetails Interface Implementation
    // -------------------------------------------------------------------------

    /**
     * Returns the authorities (permissions) granted to the user.
     * <p>
     * Currently simplified: All registered users are assigned the default "ROLE_USER".
     *
     * @return A collection containing the single generic user role.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Indicates whether the user's account has expired.
     * <p>
     * Currently hardcoded to {@code true} (account never expires).
     *
     * @return {@code true} if the account is valid.
     */
    @Override
    public boolean isAccountNonExpired() { return true; }

    /**
     * Indicates whether the user is locked or unlocked.
     * <p>
     * Currently hardcoded to {@code true} (account is never locked).
     * In a production environment, this could be tied to failed login attempts.
     *
     * @return {@code true} if the account is not locked.
     */
    @Override
    public boolean isAccountNonLocked() { return true; }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * <p>
     * Currently hardcoded to {@code true} (password never expires).
     *
     * @return {@code true} if the credentials are valid.
     */
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    /**
     * Indicates whether the user is enabled or disabled.
     * <p>
     * Currently hardcoded to {@code true}. Could be used for email verification workflows.
     *
     * @return {@code true} if the user is enabled.
     */
    @Override
    public boolean isEnabled() { return true; }
}