package com.example.splitwise.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_balances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private User counterUser;

    @ManyToOne
    private Group group;

    private BigDecimal amount;
}
