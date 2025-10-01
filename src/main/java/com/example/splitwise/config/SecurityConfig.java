package com.example.splitwise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for Splitwise application.
 * Configures OAuth2 login, role-based access, and endpoint security.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures HTTP security for the application.
     *
     * - Permits access to /auth/** endpoints
     * - Secures all other endpoints
     * - Enables OAuth2 login with Google
     *
     * @param http HttpSecurity object
     * @return configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity (can enable in production)
            .csrf(csrf -> csrf.disable())
            
            // Authorize requests
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            
            // OAuth2 login
            .oauth2Login(Customizer.withDefaults())
            
            // Logout support
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            );

        return http.build();
    }

    /**
     * Password encoder bean (for future use if needed for local login)
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
