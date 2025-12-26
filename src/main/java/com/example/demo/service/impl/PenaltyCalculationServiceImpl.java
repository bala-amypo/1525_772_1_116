package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.PenaltyCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {

    private PenaltyCalculationRepository penaltyCalculationRepository;
    private ContractRepository contractRepository;
    private DeliveryRecordRepository deliveryRecordRepository;
    private BreachRuleRepository breachRuleRepository;

    public PenaltyCalculationServiceImpl() {
    }

    public PenaltyCalculationServiceImpl(
            PenaltyCalculationRepository penaltyCalculationRepository,
            ContractRepository contractRepository,
            DeliveryRecordRepository deliveryRecordRepository,
            BreachRuleRepository breachRuleRepository) {

        this.penaltyCalculationRepository = penaltyCalculationRepository;
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
        this.breachRuleRepository = breachRuleRepository;
    }

    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        DeliveryRecord record = deliveryRecordRepository
                .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("No delivery record"));

        BreachRule rule = breachRuleRepository
                .findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active breach rule"));

        long diff = ChronoUnit.DAYS.between(
                contract.getAgreedDeliveryDate(),
                record.getDeliveryDate()
        );

        int daysDelayed = (int) Math.max(0, diff);

        BigDecimal penalty = rule.getPenaltyPerDay()
                .multiply(BigDecimal.valueOf(daysDelayed));

        BigDecimal maxAllowed = contract.getBaseContractValue()
                .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage() / 100));

        if (penalty.compareTo(maxAllowed) > 0) {
            penalty = maxAllowed;
        }

        PenaltyCalculation calc = PenaltyCalculation.builder()
                .contract(contract)
                .deliveryRecord(record)
                .breachRule(rule)
                .daysDelayed(daysDelayed)
                .calculatedPenalty(penalty)
                .build();

        return penaltyCalculationRepository.save(calc);
    }

    @Override
    public PenaltyCalculation getCalculationById(Long id) {
        return penaltyCalculationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found"));
    }

    @Override
    public List<PenaltyCalculation> getCalculationsForContract(Long contractId) {
        return penaltyCalculationRepository.findByContractId(contractId);
    }
}
