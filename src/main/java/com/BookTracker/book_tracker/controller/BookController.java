package com.BookTracker.book_tracker.controller;

import com.BookTracker.book_tracker.model.Book;
import com.BookTracker.book_tracker.model.User;
import com.BookTracker.book_tracker.service.IBookService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;


/**
 * Controller responsible for managing the Book Tracker web interface.
 * <p>
 * This class handles the HTTP requests for listing, creating, updating, and deleting books.
 * It implements pagination, sorting, dynamic filtering, and enforcing "Demo Mode" restrictions
 * for guest users.
 */
@Controller
@RequestMapping("/index")
public class BookController {

    private final IBookService bookService;

    /**
     * Constructs the controller with the required business service.
     *
     * @param bookService The service layer for handling book logic.
     */
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves a paginated and filtered list of books associated with the authenticated user.
     * <p>
     * Populates the model with the book list and all current filter/sort parameters
     * to maintain the state of the UI (search bars, sort arrows, etc.).
     *
     * @param model      The UI model to pass attributes to the view.
     * @param user       The currently authenticated user (used to fetch only their books).
     * @param page       The page number to retrieve (0-indexed, defaults to 0).
     * @param size       The number of items per page (defaults to 10).
     * @param title      Optional filter for book title.
     * @param author     Optional filter for book author.
     * @param finishDate Optional filter for the date the book was finished.
     * @param score      Optional filter for the book rating.
     * @param sortField  The field to sort by (defaults to "createdAt").
     * @param sortDir    The direction of sorting ("asc" or "desc").
     * @return The name of the Thymeleaf view ("index") to render.
     */
    @GetMapping
    public String listBooks(Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate,
                            @RequestParam(required = false) Integer score,
                            @RequestParam(required = false) String sortField,
                            @RequestParam(required = false) String sortDir) {

        // Default sorting logic: Newest created first
        if(sortField == null || sortField.isEmpty()) {
            sortField = "createdAt";
            sortDir = "desc";
        }

        Pageable pageable = PageRequest.of(page, size,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

        Page<Book> bookPage = bookService.findByFilters(title, author, finishDate, score, user, pageable);

        // Logic for UI toggle arrows (if current is asc, next click should be desc)
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

    /**
     * Prepares the view for creating a new book entry.
     *
     * @param model The model to hold the empty Book object for binding.
     * @return The name of the creation view ("new").
     */
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "new";
    }

    /**
     * Processes the submission of a new book.
     * <p>
     * Includes a security check for the "guest" account to prevent database writes in Demo Mode.
     *
     * @param book               The book object populated from the form data.
     * @param result             Container for validation errors (e.g., mandatory fields).
     * @param user               The authenticated user who will own the book.
     * @param authentication     Used to check the username for Demo Mode restrictions.
     * @param redirectAttributes Used to pass flash messages (feedback) after redirection.
     * @return A redirect to the index page on success/block, or the "new" view on validation failure.
     */
    @PostMapping("/new")
    public String createBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            @AuthenticationPrincipal User user,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "new";
        }
        // Demo Mode Protection
        if (authentication != null && authentication.getName().equals("guest")) {
            redirectAttributes.addFlashAttribute("demoMessage", "Demo Mode: You can't add books to the global database, but the form validation works perfectly!");
            return "redirect:/index";
        }
        book.setUser(user);
        bookService.save(book);
        return "redirect:/index";
    }

    /**
     * Retrieves an existing book by ID and prepares the edit view.
     *
     * @param id    The unique identifier of the book to edit.
     * @param model The model to hold the book data.
     * @return The name of the edit view ("edit").
     */
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "edit";
    }

    /**
     * Processes the update of an existing book.
     * <p>
     * Instead of overwriting the entity entirely, this method fetches the existing record
     * and updates only the editable fields to preserve immutable data (like creation date or owner).
     * Also enforces Demo Mode restrictions.
     *
     * @param book               The form data containing updated values.
     * @param result             Validation errors container.
     * @param user               The current user (unused in logic but available).
     * @param authentication     Used to check for "guest" restrictions.
     * @param redirectAttributes Used for user feedback messages.
     * @return Redirect to index on success/block, or "edit" view on error.
     */
    @PostMapping("/edit")
    public String updateBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            @AuthenticationPrincipal User user, Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "edit";
        }
        // Demo Mode Protection
        if (authentication != null && "guest".equals(authentication.getName())) {
            redirectAttributes.addFlashAttribute("demoMessage", "Demo Mode: Changes are not saved. Create an account to edit books.");
            return "redirect:/index";
        }
        Book bookExistente = bookService.findById(book.getId());

        // Update only allowed fields
        bookExistente.setTitle(book.getTitle());
        bookExistente.setAuthor(book.getAuthor());
        bookExistente.setGenre(book.getGenre());
        bookExistente.setFinishDate(book.getFinishDate());
        bookExistente.setScore(book.getScore());
        bookExistente.setReview(book.getReview());

        // 'createdAt' and 'user' are preserved from the existing entity
        bookService.save(bookExistente);
        return "redirect:/index";
    }

    /**
     * Deletes a book from the system.
     * <p>
     * This operation is disabled for the "guest" user to maintain data integrity in the shared environment.
     *
     * @param id                 The ID of the book to delete.
     * @param authentication     Used to check for "guest" restrictions.
     * @param redirectAttributes Used to display the denial message in Demo Mode.
     * @return Redirect to the index page.
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        // Demo Mode Protection
        if (authentication != null && "guest".equals(authentication.getName())) {
            redirectAttributes.addFlashAttribute("demoMessage", "Demo Mode: Deletion is disabled in the shared enviroment.");
            return "redirect:/index";
        }
        bookService.deleteById(id);
        return "redirect:/index";
    }

    /**
     * Resets all active filters and pagination settings.
     *
     * @return Redirects to the index page with default parameters (Page 0, Size 10).
     */
    @GetMapping("/clear")
    public String clearFilters() {
        return "redirect:/index?page=0&size=10";
    }
}
