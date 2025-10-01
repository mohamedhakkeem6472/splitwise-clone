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

/**
 * Service for managing expenses.
 */
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final BalanceService balanceService;

    /**
     * Creates an expense and updates balances.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Expense createExpense(Long groupId,
                                 Long createdById,
                                 String title,
                                 double totalAmount,
                                 Map<Long, Double> customShares) {

        // Validate group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Validate user
        User createdBy = userRepository.findById(createdById)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!group.getMembers().contains(createdBy)) {
            throw new IllegalArgumentException("User is not a member of this group");
        }

        // Create expense
        Expense expense = new Expense();
        expense.setGroup(group);
        expense.setCreatedBy(createdBy);
        expense.setTitle(title);
        expense.setTotalAmount(totalAmount);

        Expense savedExpense = expenseRepository.save(expense);

        // Split expense
        Map<User, Double> shares = calculateShares(group, totalAmount, customShares);

        // Update balances in new transaction for each user
        shares.forEach((user, amount) ->
                balanceService.updateBalance(group, createdBy, user, amount));

        return savedExpense;
    }

    /**
     * Calculates each user's share in a group.
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ,  readOnly = true)
    public Map<User, Double> calculateShares(Group group,
                                             double totalAmount,
                                             Map<Long, Double> customShares) {

        Map<User, Double> shares = new HashMap<>();

        if (customShares != null && !customShares.isEmpty()) {
            customShares.forEach((userId, shareAmount) -> {
                Optional<User> userOpt = userRepository.findById(userId);
                userOpt.ifPresent(user -> shares.put(user, shareAmount));
            });
        } else {
            double equalShare = totalAmount / group.getMembers().size();
            group.getMembers().forEach(user -> shares.put(user, equalShare));
        }

        return shares;
    }
}
