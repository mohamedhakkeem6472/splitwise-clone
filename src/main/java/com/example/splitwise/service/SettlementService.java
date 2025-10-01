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

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final BalanceService balanceService;

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Settlement settleAmount(Long expenseId, Long payerId, Long receiverId, BigDecimal amount) {

        Expense expense = expenseRepository.findById(expenseId).orElseThrow();
        User payer = userRepository.findById(payerId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();

        Settlement settlement = Settlement.builder()
                .expense(expense)
                .payer(payer)
                .receiver(receiver)
                .amount(amount)
                .build();

        Settlement saved = settlementRepository.save(settlement);

        balanceService.updateBalanceAfterSettlement(payer, receiver, amount);

        return saved;
    }
}
