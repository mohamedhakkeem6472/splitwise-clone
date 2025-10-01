package com.example.splitwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Splitwise Clone application.
 * 
 * Features:
 * - Spring Boot backend for expense sharing
 * - Google OAuth2 login
 * - Transactional expense and settlement management
 */
@SpringBootApplication
public class SplitwiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitwiseApplication.class, args);
    }
}
