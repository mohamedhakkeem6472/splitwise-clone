package com.example.splitwise.service;

import com.example.splitwise.model.Expense;
import com.example.splitwise.model.Settlement;
import com.example.splitwise.model.User;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.SettlementRepository;
import com.example.splitwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Service for handling settlements between users.
 */
@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final BalanceService balanceService;

    /**
     * Settles a payment between two users.
     * Uses SERIALIZABLE isolation to prevent double settlements.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Settlement settleBalance(Long payerId, Long receiverId, BigDecimal amount, Long expenseId) {

        User payer = userRepository.findById(payerId)
                .orElseThrow(() -> new IllegalArgumentException("Payer not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));

        // Adjust balances
        balanceService.updateBalanceAfterSettlement(payer, receiver, amount);

        Settlement settlement = Settlement.builder()
                .payer(payer)
                .receiver(receiver)
                .expense(expense)
                .amount(amount)
                .build();

        return settlementRepository.save(settlement);
    }
}
