package com.example.splitwise.controller;

import com.example.splitwise.model.Settlement;
import com.example.splitwise.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping
    public ResponseEntity<Settlement> settle(@RequestBody Map<String, Object> payload) {
        Long payerId = ((Number) payload.get("payerId")).longValue();
        Long receiverId = ((Number) payload.get("receiverId")).longValue();
        BigDecimal amount = new BigDecimal(payload.get("amount").toString());

        return ResponseEntity.ok(settlementService.settle(payerId, receiverId, amount));
    }
}
