package com.BookTracker.book_tracker.service;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService{

    private final BookRepository repository;

    public BookService(BookRepository repository){
        this.repository = repository;
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
}
