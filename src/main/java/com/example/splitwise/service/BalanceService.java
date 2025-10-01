package com.example.splitwise.service;

import com.example.splitwise.model.Group;
import com.example.splitwise.model.User;
import com.example.splitwise.model.UserBalance;
import com.example.splitwise.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void updateBalance(Group group, User paidBy, User owedBy, double amount) {
        if (paidBy.equals(owedBy)) return;

        UserBalance balance = userBalanceRepository
                .findByGroupAndUserAndCounterUser(group, owedBy, paidBy)
                .orElse(UserBalance.builder()
                        .group(group)
                        .user(owedBy)
                        .counterUser(paidBy)
                        .amount(BigDecimal.ZERO)
                        .build());

        balance.setAmount(balance.getAmount().add(BigDecimal.valueOf(amount)));
        userBalanceRepository.save(balance);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void updateBalanceAfterSettlement(User payer, User receiver, BigDecimal amount) {
        UserBalance balance = userBalanceRepository
                .findByUserAndCounterUser(payer, receiver)
                .orElse(UserBalance.builder()
                        .user(payer)
                        .counterUser(receiver)
                        .amount(BigDecimal.ZERO)
                        .build());

        BigDecimal updated = balance.getAmount().subtract(amount);
        if (updated.compareTo(BigDecimal.ZERO) < 0) updated = BigDecimal.ZERO;
        balance.setAmount(updated);
        userBalanceRepository.save(balance);
    }
}
