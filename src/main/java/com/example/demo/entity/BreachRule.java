package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class BreachRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    private BigDecimal penaltyPerDay;
    private Double maxPenaltyPercentage;
    private Boolean active;
    private Boolean isDefaultRule;

    public Long getId() { return id; }
    public BigDecimal getPenaltyPerDay() { return penaltyPerDay; }
    public Double getMaxPenaltyPercentage() { return maxPenaltyPercentage; }
    public Boolean getActive() { return active; }
}
