package com.example.demo.repository;

import com.example.demo.entity.PenaltyCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PenaltyCalculationRepository extends JpaRepository<PenaltyCalculation, Long> {

    List<PenaltyCalculation> findByContractId(Long contractId);

    Optional<PenaltyCalculation> findTopByContractIdOrderByCalculatedAtDesc(Long contractId);
}
