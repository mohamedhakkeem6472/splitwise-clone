package com.example.splitwise.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;



@Data
public class SettlementRequest {
    private Long expenseId;
    private Long payerId;
    private Long receiverId;
    private double amount;
}
