package com.example.splitwise.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "settlements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User payer;

    @ManyToOne
    private User receiver;

    private BigDecimal amount;

    @ManyToOne
    private Expense expense;
}
