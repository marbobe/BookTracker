package com.BookTracker.book_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    private LocalDate finishDate;
    @Min(1) @Max(5)
    private Integer score;
    @Column(length = 2000)
    private String review;

}
