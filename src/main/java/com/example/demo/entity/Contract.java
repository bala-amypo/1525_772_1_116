package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String contractNumber;

    private String title;
    private String counterpartyName;

    @Temporal(TemporalType.DATE)
    private Date agreedDeliveryDate;

    private BigDecimal baseContractValue;
    private String status;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        status = "ACTIVE";
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() { return id; }
    public Date getAgreedDeliveryDate() { return agreedDeliveryDate; }
    public BigDecimal getBaseContractValue() { return baseContractValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
