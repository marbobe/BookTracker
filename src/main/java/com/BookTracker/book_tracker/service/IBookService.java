package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IBookService {

    public Page<Book> getBooks(Pageable pageable);
    public List<Book> findAll();
    public Book findById(Long id);
    public void save(Book book);
    public void deleteById(Long id);
    public Long getSequenceNumber(LocalDateTime date, Long id, User user);

    Page<Book> findByFilters(String title, String author, LocalDate finishDate, Integer score, Pageable pageable);

    Page<Book> findByFilters(String title, String author, LocalDate finishDate, Integer score, User user, Pageable pageable);
}
