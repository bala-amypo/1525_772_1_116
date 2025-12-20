package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class PenaltyCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contract contract;

    private Integer daysDelayed;
    private BigDecimal calculatedPenalty;

    @ManyToOne
    private BreachRule appliedRule;

    private Timestamp calculatedAt;

    @PrePersist
    void onCreate() {
        calculatedAt = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() { return id; }
    public Integer getDaysDelayed() { return daysDelayed; }
    public BigDecimal getCalculatedPenalty() { return calculatedPenalty; }
}
