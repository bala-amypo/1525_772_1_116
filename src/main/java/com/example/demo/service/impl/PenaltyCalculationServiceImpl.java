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

    private final PenaltyCalculationRepository penaltyCalculationRepository;
    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final BreachRuleRepository breachRuleRepository;

    public PenaltyCalculationServiceImpl(PenaltyCalculationRepository penaltyCalculationRepository,
                                         ContractRepository contractRepository,
                                         DeliveryRecordRepository deliveryRecordRepository,
                                         BreachRuleRepository breachRuleRepository) {
        this.penaltyCalculationRepository = penaltyCalculationRepository;
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
        this.breachRuleRepository = breachRuleRepository;
    }

    public PenaltyCalculationServiceImpl() {
        this.penaltyCalculationRepository = null;
        this.contractRepository = null;
        this.deliveryRecordRepository = null;
        this.breachRuleRepository = null;
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

        long daysDelayed = Math.max(0,
                ChronoUnit.DAYS.between(contract.getAgreedDeliveryDate(), record.getDeliveryDate()));

        BigDecimal rawPenalty =
                rule.getPenaltyPerDay().multiply(BigDecimal.valueOf(daysDelayed));

        BigDecimal maxPenalty =
                contract.getBaseContractValue()
                        .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage()))
                        .divide(BigDecimal.valueOf(100));

        BigDecimal finalPenalty = rawPenalty.min(maxPenalty);

        PenaltyCalculation calc = PenaltyCalculation.builder()
                .contract(contract)
                .deliveryRecord(record)
                .breachRule(rule)
                .daysDelayed((int) daysDelayed)
                .calculatedPenalty(finalPenalty)
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
