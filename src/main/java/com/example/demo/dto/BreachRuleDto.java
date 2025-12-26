package com.example.demo.dto;

import java.math.BigDecimal;

public class BreachRuleDto {

    private Long id;
    private String ruleName;
    private BigDecimal penaltyPerDay;
    private Double maxPenaltyPercentage;
    private Boolean active;
    private Boolean isDefaultRule;

    public BreachRuleDto(){}

    public BreachRuleDto(Long id, String ruleName,
                         BigDecimal penaltyPerDay,
                         Double maxPenaltyPercentage,
                         Boolean active,
                         Boolean isDefaultRule) {
        this.id = id;
        this.ruleName = ruleName;
        this.penaltyPerDay = penaltyPerDay;
        this.maxPenaltyPercentage = maxPenaltyPercentage;
        this.active = active;
        this.isDefaultRule = isDefaultRule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getMaxPenaltyPercentage() {
        return maxPenaltyPercentage;
    }

    public void setMaxPenaltyPercentage(Double maxPenaltyPercentage) {
        this.maxPenaltyPercentage = maxPenaltyPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getIsDefaultRule() {
        return isDefaultRule;
    }

    public void setIsDefaultRule(Boolean isDefaultRule) {
        this.isDefaultRule = isDefaultRule;
    }

    
}
