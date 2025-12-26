package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DeliveryRecordDto {
    private Long id;
    private Long contractId;
    private LocalDate deliveryDate;
    private String notes;
}
