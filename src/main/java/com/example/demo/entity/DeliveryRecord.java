package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class DeliveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contract contract;

    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    private String notes;

    public Long getId() { return id; }
    public Contract getContract() { return contract; }
    public Date getDeliveryDate() { return deliveryDate; }
}
