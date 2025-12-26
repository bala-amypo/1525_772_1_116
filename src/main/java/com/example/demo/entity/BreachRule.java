package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "breach_rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreachRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ruleName;

    @Column(nullable = false)
    private BigDecimal penaltyPerDay;

    @Column(nullable = false)
    private Double maxPenaltyPercentage;

    private Boolean active;

    private Boolean isDefaultRule;

    /* ---------------- Relationships ---------------- */

    @OneToMany(mappedBy = "breachRule", fetch = FetchType.LAZY)
    private List<PenaltyCalculation> penaltyCalculations;

    @PrePersist
    public void onCreate() {
        if (active == null) {
            active = true;
        }
        if (isDefaultRule == null) {
            isDefaultRule = false;
        }
    }
}
