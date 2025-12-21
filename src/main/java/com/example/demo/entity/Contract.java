package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts", uniqueConstraints = @UniqueConstraint(columnNames = "contractNumber"))
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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
    private String status = "ACTIVE";

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Contract() {}

    public Contract(String contractNumber, String title, String counterpartyName, LocalDate agreedDeliveryDate, BigDecimal baseContractValue) {
        this.contractNumber = contractNumber;
        this.title = title;
        this.counterpartyName = counterpartyName;
        this.agreedDeliveryDate = agreedDeliveryDate;
        this.baseContractValue = baseContractValue;
    }

    public Long getId() {
        return id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getCounterpartyName() {
        return counterpartyName;
    }

    public LocalDate getAgreedDeliveryDate() {
        return agreedDeliveryDate;
    }

    public BigDecimal getBaseContractValue() {
        return baseContractValue;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
    }

    public void setAgreedDeliveryDate(LocalDate agreedDeliveryDate) {
        this.agreedDeliveryDate = agreedDeliveryDate;
    }

    public void setBaseContractValue(BigDecimal baseContractValue) {
        this.baseContractValue = baseContractValue;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
