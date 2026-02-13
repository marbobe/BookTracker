package com.BookTracker.book_tracker.controller;

import com.BookTracker.book_tracker.model.User;
import com.BookTracker.book_tracker.model.UserRegistrationDto;
import com.BookTracker.book_tracker.repository.UserRepository;
import com.BookTracker.book_tracker.service.CustomUserDetailsService;
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

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    // Inyectamos dependencias
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Enviamos el DTO vacío en lugar del user
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

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

    @GetMapping("/login/guest")
    public String loginAsGuest(HttpServletRequest request){
        try{
            UserDetails guestUser = userDetailsService.loadUserByUsername("guest");
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    guestUser, null, guestUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

            // 2. Guardamos la sesión en el contexto de la petición
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "redirect:/index";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }
}