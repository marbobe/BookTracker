package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService{

    private final BookRepository repository;

    public BookService(BookRepository repository){
        this.repository = repository;
    }

    public Page<Book> getBooks(Pageable pageable){
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void save(Book book) {
        repository.save(book);
    }

    @Override
    public void deleteById(Integer id){
        repository.deleteById(id);
    }

    @Override
    public Page<Book> findByFilters(String title, String author, LocalDate finishDate, Integer score, Pageable pageable) {
        return repository.findByFilters(title, author, finishDate, score, pageable);
    }
}
