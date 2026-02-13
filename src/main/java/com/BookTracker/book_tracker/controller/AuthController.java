package com.BookTracker.book_tracker.controller;

import com.BookTracker.book_tracker.model.User;
import com.BookTracker.book_tracker.model.UserRegistrationDto;
import com.BookTracker.book_tracker.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * Handles user authentication and registration workflows.
 * <p>
 * This controller manages the login view, the registration process using Data Transfer Objects (DTOs),
 * and the specialized guest login mechanism.
 */
@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    /**
     * Initializes the controller with required services.
     *
     * @param userRepository     Repository for user data persistence.
     * @param passwordEncoder    Service for hashing passwords securely.
     * @param userDetailsService Service for loading user-specific data during authentication.
     */
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Serves the custom login page.
     *
     * @return The name of the login view template ("login").
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Prepares and displays the user registration form.
     * <p>
     * It initializes a new empty {@link UserRegistrationDto} and adds it to the model
     * to bind form inputs.
     *
     * @param model The UI model to pass the DTO to the view.
     * @return The name of the registration view template ("register").
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    /**
     * Processes the user registration form submission.
     * <p>
     * It validates the {@link UserRegistrationDto}, checks for username duplicates,
     * hashes the password, and persists the new user.
     *
     * @param userDto The data transfer object containing registration details.
     * @param result  Container for validation errors (e.g., password too short).
     * @param model   The UI model to pass attributes back to the view.
     * @return A redirect string to the login page upon success, or the registration view upon error.
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto userDto, BindingResult result, Model model) {

        if(result.hasErrors()){
            return "register";
        }

        if(userRepository.findByUsername(userDto.getUsername()).isPresent()){
            model.addAttribute("error", "Username already exist. Please choose another one.");
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(newUser);
        return "redirect:/login?registered=true";
    }

    /**
     * Authenticates a user as a "Guest" without requiring a password.
     * <p>
     * This method manually establishes the Spring Security context for the pre-defined guest user,
     * bypassing the standard credential check to provide quick access.
     *
     * @param request The HTTP request to retrieve and update the session.
     * @return Redirect to the home page if successful, or login page with an error parameter if failed.
     */
    @GetMapping("/login/guest")
    public String loginAsGuest(HttpServletRequest request){
        try{
            UserDetails guestUser = userDetailsService.loadUserByUsername("guest");
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    guestUser, null, guestUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "redirect:/index";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }
}