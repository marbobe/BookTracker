package com.BookTracker.book_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Represents a book entry within the tracking system.
 * <p>
 * This entity maps to the "books" table and includes advanced Hibernate features:
 * <ul>
 * <li><b>Soft Delete:</b> Records are not physically removed. Instead, the 'active' flag is set to false.</li>
 * <li><b>Automatic Filtering:</b> The {@code @SQLRestriction} ensures that queries only return active books by default.</li>
 * <li><b>Indexing:</b> A composite index is defined to optimize queries filtering by user and creation date.</li>
 * </ul>
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name= "books", indexes = {
        @Index(name = "idx_books_created_active", columnList = "user_id, active, created_at")
})
// Overrides the standard DELETE command to instead update the status
@SQLDelete(sql = "UPDATE books SET active = false WHERE id = ?")
// Automatically appends "AND active = true" to all SELECT queries for this entity
@SQLRestriction("active = true")
public class Book {

    /**
     * Unique identifier for the book.
     * <p>
     * Generated using a database sequence to ensure transactional safety and performance.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_sequence", allocationSize = 1)
    private Long id;

    /**
     * The title of the book.
     * Mandatory field.
     */
    @NotBlank(message = "Title is required")
    private String title;

    /**
     * The author of the book.
     * Mandatory field.
     */
    @NotBlank(message = "Author is required")
    private String author;

    /**
     * The literary genre of the book.
     * Stored as a String in the database for readability.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    /**
     * The date the user finished reading the book.
     * <p>
     * Validation ensures this date cannot be in the future.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Finish date cannot be in the future")
    private LocalDate finishDate;

    /**
     * The user's rating of the book (1 to 5 stars).
     */
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score cannot be more than 5")
    private Integer score;

    /**
     * A personal review or notes about the book.
     * <p>
     * The column length is set to 2000 characters to allow for detailed feedback.
     */
    @Column(length = 2000)
    private String review;

    /**
     * The user who owns this book entry.
     * <p>
     * Configured with {@code FetchType.LAZY} to prevent loading user details
     * automatically when querying books, improving performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    /**
     * Soft delete flag.
     * <p>
     * If {@code true}, the book is visible. If {@code false}, it is considered deleted
     * but remains in the database for historical/audit purposes.
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Timestamp of when the record was created.
     * <p>
     * This field is immutable (updatable = false) and set automatically before persistence.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * A temporary field used for UI numbering (e.g., displaying row numbers in a table).
     * <p>
     * Marked as {@code @Transient}, so it is not stored in the database.
     */
    @Transient
    private Long displayIndex;

    /**
     * Lifecycle hook executed before the entity is first saved to the database.
     * Sets the {@code createdAt} timestamp to the current time.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
