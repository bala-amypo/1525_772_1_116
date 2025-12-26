package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(
        name = "breach_rules",
        uniqueConstraints = @UniqueConstraint(columnNames = "ruleName")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BreachRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal penaltyPerDay;

    @Column(nullable = false)
    private Double maxPenaltyPercentage;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private Boolean isDefaultRule = false;

    /* ===================== RELATIONSHIPS ===================== */

    @OneToMany(mappedBy = "breachRule")
    private List<PenaltyCalculation> penaltyCalculations;
}
