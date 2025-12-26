package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contracts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String contractNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String counterpartyName;

    @Column(nullable = false)
    private LocalDate agreedDeliveryDate;

    @Column(nullable = false)
    private BigDecimal baseContractValue;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /* ---------------- Relationships ---------------- */

    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
    private List<DeliveryRecord> deliveryRecords;

    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
    private List<PenaltyCalculation> penaltyCalculations;

    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
    private List<BreachReport> breachReports;

    /* ---------------- Lifecycle ---------------- */

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "ACTIVE";
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
