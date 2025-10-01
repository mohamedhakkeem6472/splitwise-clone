package com.example.splitwise.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "balances", uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Balance {
    @Id @GeneratedValue private Long id;
    @ManyToOne(optional = false) private Group group;
    @ManyToOne(optional = false) private User user;
    @Column(precision=19, scale=2) private BigDecimal netAmount = BigDecimal.ZERO;
    @Version private Long version;
    
}
