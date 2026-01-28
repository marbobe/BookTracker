package com.BookTracker.book_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login","/register", "/css/**", "/js/**","/favicon.ico", "/images/**").permitAll() // Puertas abiertas para registro y estilos
                        .anyRequest().authenticated() // Todo lo demás requiere login
                )
                .formLogin(form -> form
                        .loginPage("/login") // Nuestra página personalizada
                        .defaultSuccessUrl("/index", true) // A dónde ir tras el éxito
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // NUNCA guardes contraseñas en texto plano. BCrypt las encripta.
        return new BCryptPasswordEncoder();
    }
}
