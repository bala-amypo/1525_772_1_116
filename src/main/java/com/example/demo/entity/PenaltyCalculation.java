package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalty_calculations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_record_id")
    private DeliveryRecord deliveryRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breach_rule_id", nullable = false)
    private BreachRule breachRule;

    @Column(nullable = false)
    private Integer daysDelayed;

    @Column(nullable = false)
    private BigDecimal calculatedPenalty;

    private LocalDateTime calculatedAt;

    @PrePersist
    public void onCreate() {
        this.calculatedAt = LocalDateTime.now();
    }
}
