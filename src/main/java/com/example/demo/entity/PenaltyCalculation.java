package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalty_calculations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PenaltyCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ===================== RELATIONSHIPS ===================== */

    @ManyToOne(optional = false)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "delivery_record_id")
    private DeliveryRecord deliveryRecord;

    @ManyToOne(optional = false)
    @JoinColumn(name = "breach_rule_id")
    private BreachRule breachRule;

    /* ===================== FIELDS ===================== */

    @Column(nullable = false)
    private Integer daysDelayed;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal calculatedPenalty;

    @Column(nullable = false, updatable = false)
    private LocalDateTime calculatedAt;

    @PrePersist
    protected void onCreate() {
        this.calculatedAt = LocalDateTime.now();
    }
}
