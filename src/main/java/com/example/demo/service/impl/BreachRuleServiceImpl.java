package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.service.BreachRuleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BreachRuleServiceImpl implements BreachRuleService {

    private final BreachRuleRepository breachRuleRepository;

    public BreachRuleServiceImpl(BreachRuleRepository breachRuleRepository) {
        this.breachRuleRepository = breachRuleRepository;
    }

    @Override
    public BreachRule createRule(BreachRule rule) {

        if (rule.getPenaltyPerDay() == null ||
                rule.getPenaltyPerDay().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Penalty must be greater than zero");
        }

        if (rule.getMaxPenaltyPercentage() == null ||
                rule.getMaxPenaltyPercentage() < 0 ||
                rule.getMaxPenaltyPercentage() > 100) {
            throw new IllegalArgumentException("Invalid penalty percentage");
        }

        return breachRuleRepository.save(rule);
    }

    @Override
    public BreachRule updateRule(Long id, BreachRule updatedRule) {

        BreachRule existing = breachRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        existing.setRuleName(updatedRule.getRuleName());
        existing.setPenaltyPerDay(updatedRule.getPenaltyPerDay());
        existing.setMaxPenaltyPercentage(updatedRule.getMaxPenaltyPercentage());
        existing.setActive(updatedRule.getActive());
        existing.setIsDefaultRule(updatedRule.getIsDefaultRule());

        return breachRuleRepository.save(existing);
    }

    @Override
    public BreachRule getActiveDefaultOrFirst() {
        return breachRuleRepository
                .findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active breach rule"));
    }

    @Override
    public List<BreachRule> getAllRules() {
        return breachRuleRepository.findAll();
    }

    @Override
    public void deactivateRule(Long id) {

        BreachRule rule = breachRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        rule.setActive(false);
        breachRuleRepository.save(rule);
    }
}
