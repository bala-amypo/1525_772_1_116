package com.example.demo.dto;

import java.time.LocalDate;

public class DeliveryRecordDto {

    private Long id;
    private Long contractId;
    private LocalDate deliveryDate;
    private String notes;

    public DeliveryRecordDto(){}

    public DeliveryRecordDto(Long id, Long contractId,
                             LocalDate deliveryDate, String notes) {
        this.id = id;
        this.contractId = contractId;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // getters and setters
}
