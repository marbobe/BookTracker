package com.BookTracker.book_tracker.controller;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.service.IBookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/books")
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    // LISTAR
    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate,
                            @RequestParam(required = false) Integer score,
                            @RequestParam(required = false) String sortField,
                            @RequestParam(required = false) String sortDir) {

        // Si no hay sorting, ordenar por ID
        if(sortField == null || sortField.isEmpty()) {
            sortField = "idBook";
            sortDir = "asc";
        }

        Pageable pageable = PageRequest.of(page, size,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

        Page<Book> bookPage = bookService.findByFilters(title, author, finishDate, score, pageable);

        // Para el template: invertir la dirección de la flecha
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("finishDate", finishDate);
        model.addAttribute("score", score);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "index";
    }


    // NUEVO FORM
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "new";
    }

    // GUARDAR NUEVO
    @PostMapping("/new")
    public String createBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    // FORM EDITAR
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Integer id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        model.addAttribute("book", book);
        return "edit";
    }

    // GUARDAR EDICIÓN
    @PostMapping("/edit")
    public String updateBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "edit";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    // BORRAR
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/clear")
    public String clearFilters() {
        return "redirect:/books?page=0&size=10";
    }
}
