package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BreachReportDto {

    private Long id;
    private Long contractId;
    private Integer daysDelayed;
    private BigDecimal penaltyAmount;
    private String reportStatus;
    private LocalDateTime generatedAt;

    public BreachReportDto(){}

    public BreachReportDto(Long id, Long contractId,
                           Integer daysDelayed,
                           BigDecimal penaltyAmount,
                           String reportStatus,
                           LocalDateTime generatedAt) {
        this.id = id;
        this.contractId = contractId;
        this.daysDelayed = daysDelayed;
        this.penaltyAmount = penaltyAmount;
        this.reportStatus = reportStatus;
        this.generatedAt = generatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Integer getDaysDelayed() {
        return daysDelayed;
    }

    public void setDaysDelayed(Integer daysDelayed) {
        this.daysDelayed = daysDelayed;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

   
}
