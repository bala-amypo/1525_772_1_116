package com.example.demo.service;

import com.example.demo.entity.BreachRule;
import com.example.demo.repository.BreachRuleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BreachRuleService {

    private BreachRuleRepository breachRuleRepository;

    public BreachRule saveRule(BreachRule rule) {
        return breachRuleRepository.save(rule);
    }

    public List<BreachRule> getAllRules() {
        return breachRuleRepository.findAll();
    }

    public Optional<BreachRule> getRuleById(Long id) {
        return breachRuleRepository.findById(id);
    }
}
