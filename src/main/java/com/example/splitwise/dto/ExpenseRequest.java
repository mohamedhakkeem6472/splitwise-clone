package com.example.splitwise.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;



@Data
public class ExpenseRequest {
    private String title;
    private double totalAmount;
    private Long groupId;
    private Long createdById;
    private Map<Long, Double> customShares;
}


