package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ContractDto {
    private Long id;
    private String contractNumber;
    private String title;
    private String counterpartyName;
    private LocalDate agreedDeliveryDate;
    private BigDecimal baseContractValue;
    private String status;
    private LocalDateTime createdAt;
}
