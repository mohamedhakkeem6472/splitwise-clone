package com.example.splitwise.controller;

import com.example.splitwise.dto.SettlementRequest;
import com.example.splitwise.model.Settlement;
import com.example.splitwise.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping("/settle")
    public ResponseEntity<Settlement> settleAmount(@RequestBody SettlementRequest request) {
        Settlement settlement = settlementService.settleAmount(
                request.getExpenseId(),
                request.getPayerId(),
                request.getReceiverId(),
                BigDecimal.valueOf(request.getAmount())
        );
        return ResponseEntity.ok(settlement);
    }
}
