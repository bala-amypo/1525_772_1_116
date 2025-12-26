package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "breach_reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreachReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(nullable = false)
    private Integer daysDelayed;

    @Column(nullable = false)
    private BigDecimal penaltyAmount;

    private String reportStatus;

    private LocalDateTime generatedAt;

    @PrePersist
    public void onCreate() {
        this.generatedAt = LocalDateTime.now();
        if (this.reportStatus == null) {
            this.reportStatus = "GENERATED";
        }
    }
}
