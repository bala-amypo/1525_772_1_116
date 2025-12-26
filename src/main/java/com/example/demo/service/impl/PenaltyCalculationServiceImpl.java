package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.entity.Contract;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.repository.ContractRepository;
import com.example.demo.service.PenaltyCalculationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {

    private ContractRepository contractRepository;
    private BreachRuleRepository breachRuleRepository;

    @Override
    public BigDecimal calculatePenalty(Long contractId) {

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        BreachRule rule = breachRuleRepository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Breach rule not configured"));

        LocalDate dueDate = contract.getDueDate();
        LocalDate today = LocalDate.now();

        if (!today.isAfter(dueDate)) {
            return BigDecimal.ZERO;
        }

        long delayedDays = ChronoUnit.DAYS.between(dueDate, today);

        BigDecimal dailyPenalty = rule.getPenaltyPerDay();
        BigDecimal baseValue = contract.getContractValue();

        BigDecimal penalty = dailyPenalty.multiply(BigDecimal.valueOf(delayedDays));

        BigDecimal maxPenalty = baseValue
                .multiply(rule.getMaxPenaltyPercentage())
                .divide(BigDecimal.valueOf(100));

        if (penalty.compareTo(maxPenalty) > 0) {
            penalty = maxPenalty;
        }

        return penalty;
    }
}
