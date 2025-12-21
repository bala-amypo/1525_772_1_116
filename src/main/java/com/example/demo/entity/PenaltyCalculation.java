package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalty_calculations")
public class PenaltyCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "delivery_record_id")
    private DeliveryRecord deliveryRecord;

    @ManyToOne(optional = false)
    @JoinColumn(name = "breach_rule_id", nullable = false)
    private BreachRule breachRule;

    @Column(nullable = false)
    private Integer daysDelayed;

    @Column(nullable = false)
    private BigDecimal calculatedPenalty;

    @Column(updatable = false)
    private LocalDateTime calculatedAt;

    public PenaltyCalculation() {}

    public PenaltyCalculation(Contract contract,
                              DeliveryRecord deliveryRecord,
                              BreachRule breachRule,
                              Integer daysDelayed,
                              BigDecimal calculatedPenalty) {
        this.contract = contract;
        this.deliveryRecord = deliveryRecord;
        this.breachRule = breachRule;
        this.daysDelayed = daysDelayed;
        this.calculatedPenalty = calculatedPenalty;
    }

    @PrePersist
    protected void onCreate() {
        this.calculatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Contract getContract() {
        return contract;
    }

    public DeliveryRecord getDeliveryRecord() {
        return deliveryRecord;
    }

    public BreachRule getBreachRule() {
        return breachRule;
    }

    public Integer getDaysDelayed() {
        return daysDelayed;
    }

    public BigDecimal getCalculatedPenalty() {
        return calculatedPenalty;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void setDeliveryRecord(DeliveryRecord deliveryRecord) {
        this.deliveryRecord = deliveryRecord;
    }

    public void setBreachRule(BreachRule breachRule) {
        this.breachRule = breachRule;
    }

    public void setDaysDelayed(Integer daysDelayed) {
        this.daysDelayed = daysDelayed;
    }

    public void setCalculatedPenalty(BigDecimal calculatedPenalty) {
        this.calculatedPenalty = calculatedPenalty;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    
}
