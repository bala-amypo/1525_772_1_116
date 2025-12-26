package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContractDto {

    private Long id;
    private String contractNumber;
    private String title;
    private String counterpartyName;
    private LocalDate agreedDeliveryDate;
    private BigDecimal baseContractValue;
    private String status;
    private LocalDateTime createdAt;

    public ContractDto() {}

    public ContractDto(Long id, String contractNumber, String title,
                       String counterpartyName, LocalDate agreedDeliveryDate,
                       BigDecimal baseContractValue, String status,
                       LocalDateTime createdAt) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.title = title;
        this.counterpartyName = counterpartyName;
        this.agreedDeliveryDate = agreedDeliveryDate;
        this.baseContractValue = baseContractValue;
        this.status = status;
        this.createdAt = createdAt;
    }

    // getters and setters
}
