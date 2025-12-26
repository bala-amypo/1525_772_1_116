package com.example.demo.controller;

import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.service.PenaltyCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calculations")
public class PenaltyCalculationController {

    private final PenaltyCalculationService penaltyCalculationService;

    public PenaltyCalculationController(PenaltyCalculationService penaltyCalculationService) {
        this.penaltyCalculationService = penaltyCalculationService;
    }

    @PostMapping("/contract/{contractId}")
    public ResponseEntity<PenaltyCalculation> calculate(@PathVariable Long contractId) {
        return ResponseEntity.ok(
                penaltyCalculationService.calculatePenalty(contractId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenaltyCalculation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                penaltyCalculationService.getCalculationById(id)
        );
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<PenaltyCalculation>> getByContract(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(
                penaltyCalculationService.getCalculationsForContract(contractId)
        );
    }
}
