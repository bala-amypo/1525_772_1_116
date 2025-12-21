package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "breach_rules",
        uniqueConstraints = @UniqueConstraint(columnNames = "ruleName")
)
public class BreachRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleName;

    @Column(nullable = false)
    private BigDecimal penaltyPerDay;

    @Column(nullable = false)
    private Double maxPenaltyPercentage;

    @Column(nullable = false)
    private Boolean active = true;

    private Boolean isDefaultRule = false;

    public BreachRule() {}

    public BreachRule(String ruleName, BigDecimal penaltyPerDay, Double maxPenaltyPercentage) {
        this.ruleName = ruleName;
        this.penaltyPerDay = penaltyPerDay;
        this.maxPenaltyPercentage = maxPenaltyPercentage;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public BigDecimal getPenaltyPerDay() {
        return penaltyPerDay;
    }

    public Double getMaxPenaltyPercentage() {
        return maxPenaltyPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getIsDefaultRule() {
        return isDefaultRule;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setPenaltyPerDay(BigDecimal penaltyPerDay) {
        this.penaltyPerDay = penaltyPerDay;
    }

    public void setMaxPenaltyPercentage(Double maxPenaltyPercentage) {
        this.maxPenaltyPercentage = maxPenaltyPercentage;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setIsDefaultRule(Boolean isDefaultRule) {
        this.isDefaultRule = isDefaultRule;
    }

    
}
