package com.BookTracker.book_tracker.controller;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String start(ModelMap model){
        List<Book> books = bookService.findAll();
        books.forEach((book)-> logger.info(book.toString()));
        model.put("books", books);
        return "index"; //index.html
    }

    @GetMapping("/new")
    public String showAdd(){
        return "new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("bookForm") Book book){
        logger.info("Book to add: " + book);
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") int idBook, ModelMap model){

        Optional<Book> book = bookService.findById(idBook);
        logger.info("Book to edit: "+book);
        model.put("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("book") Book book){
        logger.info("Book to edit: " + book);
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") int idBook){
        bookService.deleteById(idBook);
        return "redirect:/";
    }
}
