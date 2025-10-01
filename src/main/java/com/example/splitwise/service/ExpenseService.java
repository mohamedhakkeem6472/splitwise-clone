package com.example.splitwise.service;

import com.example.splitwise.model.Expense;
import com.example.splitwise.model.Group;
import com.example.splitwise.model.User;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final BalanceService balanceService;

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Expense createExpense(Long groupId, Long createdById, String title, double totalAmount,
                                 Map<Long, Double> customShares) {

        Group group = groupRepository.findById(groupId).orElseThrow();
        User createdBy = userRepository.findById(createdById).orElseThrow();

        Expense expense = Expense.builder()
                .group(group)
                .createdBy(createdBy)
                .title(title)
                .totalAmount(totalAmount)
                .build();
        Expense savedExpense = expenseRepository.save(expense);

        Map<User, Double> shares = calculateShares(group, totalAmount, customShares);
        shares.forEach((user, amount) -> balanceService.updateBalance(group, createdBy, user, amount));

        return savedExpense;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<User, Double> calculateShares(Group group, double totalAmount, Map<Long, Double> customShares) {
        Map<User, Double> shares = new HashMap<>();
        if (customShares != null && !customShares.isEmpty()) {
            customShares.forEach((userId, amount) -> userRepository.findById(userId).ifPresent(user -> shares.put(user, amount)));
        } else {
            double equalShare = totalAmount / group.getMembers().size();
            group.getMembers().forEach(user -> shares.put(user, equalShare));
        }
        return shares;
    }
}
