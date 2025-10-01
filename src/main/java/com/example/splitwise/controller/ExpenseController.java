package com.example.splitwise.controller;

import com.example.splitwise.dto.ExpenseRequest;
import com.example.splitwise.model.Expense;
import com.example.splitwise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseRequest request) {
        Expense expense = expenseService.createExpense(
                request.getGroupId(),
                request.getCreatedById(),
                request.getTitle(),
                request.getTotalAmount(),
                request.getCustomShares()
        );
        return ResponseEntity.ok(expense);
    }
}
