package com.example.demo.service;

import com.example.demo.entity.BreachRule;

import java.util.List;

public interface BreachRuleService {

    BreachRule createRule(BreachRule rule);

    BreachRule updateRule(Long id, BreachRule rule);

    BreachRule getActiveDefaultOrFirst();

    List<BreachRule> getAllRules();

    void deactivateRule(Long id);
}
