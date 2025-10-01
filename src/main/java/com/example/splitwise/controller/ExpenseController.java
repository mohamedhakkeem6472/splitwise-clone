package com.example.splitwise.controller;

import com.example.splitwise.model.Expense;
import com.example.splitwise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Map<String, Object> payload) {
        Long groupId = ((Number) payload.get("groupId")).longValue();
        Long userId = ((Number) payload.get("userId")).longValue();
        BigDecimal amount = new BigDecimal(payload.get("amount").toString());
        String description = (String) payload.get("description");

        return ResponseEntity.ok(expenseService.addExpense(groupId, userId, amount, description));
    }
}
