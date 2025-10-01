package com.example.splitwise.service;

import com.example.splitwise.model.Settlement;
import com.example.splitwise.model.User;
import com.example.splitwise.repository.SettlementRepository;
import com.example.splitwise.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;

    @Transactional
    public Settlement settle(Long payerId, Long receiverId, BigDecimal amount) {
        User payer = userRepository.findById(payerId)
                .orElseThrow(() -> new RuntimeException("Payer not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Settlement settlement = Settlement.builder()
                .payer(payer)
                .receiver(receiver)
                .amount(amount)
                .build();

        return settlementRepository.save(settlement);
    }
}
