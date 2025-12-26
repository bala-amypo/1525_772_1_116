package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "breach_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BreachReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ===================== RELATIONSHIPS ===================== */

    @ManyToOne(optional = false)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    /* ===================== FIELDS ===================== */

    @Column(nullable = false)
    private Integer daysDelayed;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal penaltyAmount;

    @Column(nullable = false)
    private String reportStatus = "GENERATED";

    @Column(nullable = false, updatable = false)
    private LocalDateTime generatedAt;

    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }
}
