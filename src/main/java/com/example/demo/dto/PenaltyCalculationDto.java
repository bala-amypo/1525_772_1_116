package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PenaltyCalculationDto {
    private Long id;
    private Long contractId;
    private Integer daysDelayed;
    private BigDecimal calculatedPenalty;
    private LocalDateTime calculatedAt;
}
