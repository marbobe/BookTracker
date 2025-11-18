package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBookService {

    public Page<Book> getBooks(Pageable pageable);
    public List<Book> findAll();
    public Optional<Book> findById(Integer id);
    public void save(Book book);
    public void deleteById(Integer id);

    Page<Book> findByFilters(String title, String author, LocalDate finishDate, Integer score, Pageable pageable);
}
