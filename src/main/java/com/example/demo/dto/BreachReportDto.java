package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BreachReportDto {
    private Long id;
    private Long contractId;
    private Integer daysDelayed;
    private BigDecimal penaltyAmount;
    private String reportStatus;
    private LocalDateTime generatedAt;
}
