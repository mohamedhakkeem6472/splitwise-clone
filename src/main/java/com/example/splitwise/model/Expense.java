package com.example.splitwise.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expenses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private double totalAmount;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Group group;
}
