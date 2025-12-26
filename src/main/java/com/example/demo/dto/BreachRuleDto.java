package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BreachRuleDto {
    private Long id;
    private String ruleName;
    private BigDecimal penaltyPerDay;
    private Double maxPenaltyPercentage;
    private Boolean active;
    private Boolean isDefaultRule;
}
