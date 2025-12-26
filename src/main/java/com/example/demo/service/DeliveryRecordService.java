package com.example.demo.service;

import com.example.demo.entity.DeliveryRecord;
import com.example.demo.repository.DeliveryRecordRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordService {

    private DeliveryRecordRepository deliveryRecordRepository;

    public DeliveryRecord saveDelivery(DeliveryRecord record) {
        return deliveryRecordRepository.save(record);
    }

    public List<DeliveryRecord> getAllDeliveries() {
        return deliveryRecordRepository.findAll();
    }
}
