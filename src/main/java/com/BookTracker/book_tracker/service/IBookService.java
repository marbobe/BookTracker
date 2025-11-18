package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    public List<Book> findAll();
    public Optional<Book> findById(Integer id);
    public void save(Book book);
    public void deleteById(Integer id);
}
