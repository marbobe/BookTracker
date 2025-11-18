package com.BookTracker.book_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBook;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    private String genre;
    @PastOrPresent(message = "Finish date cannot be in the future")
    private LocalDate finishDate;
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score cannot be more than 5")
    private Integer score;
    @Column(length = 2000)
    private String review;

}
