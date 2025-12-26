package com.example.demo.repository;

import com.example.demo.entity.BreachReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreachReportRepository extends JpaRepository<BreachReport, Long> {

    List<BreachReport> findByContractId(Long contractId);
}
