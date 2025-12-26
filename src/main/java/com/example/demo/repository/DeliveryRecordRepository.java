package com.example.demo.repository;

import com.example.demo.entity.DeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {

    List<DeliveryRecord> findByContractId(Long contractId);

    List<DeliveryRecord> findByContractIdOrderByDeliveryDateAsc(Long contractId);

    Optional<DeliveryRecord> findFirstByContractIdOrderByDeliveryDateDesc(Long contractId);
}
