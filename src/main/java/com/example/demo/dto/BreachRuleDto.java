package com.example.demo.dto;

import java.math.BigDecimal;

public class BreachRuleDto {
    private String ruleName;
    private BigDecimal penaltyPerDay;
    private double maxPenaltyPercentage;
    private boolean isActive;
    private boolean isDefaultRule;

    
    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public BigDecimal getPenaltyPerDay() {
        return penaltyPerDay;
    }

    public void setPenaltyPerDay(BigDecimal penaltyPerDay) {
        this.penaltyPerDay = penaltyPerDay;
    }

    public double getMaxPenaltyPercentage() {
        return maxPenaltyPercentage;
    }

    public void setMaxPenaltyPercentage(double maxPenaltyPercentage) {
        this.maxPenaltyPercentage = maxPenaltyPercentage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDefaultRule() {
        return isDefaultRule;
    }

    public void setDefaultRule(boolean defaultRule) {
        isDefaultRule = defaultRule;
    }
}
