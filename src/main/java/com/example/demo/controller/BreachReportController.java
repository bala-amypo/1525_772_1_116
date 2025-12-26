package com.example.demo.controller;

import com.example.demo.entity.BreachReport;
import com.example.demo.service.BreachReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/breach-reports")
public class BreachReportController {

    private final BreachReportService breachReportService;

    public BreachReportController(BreachReportService breachReportService) {
        this.breachReportService = breachReportService;
    }

    @PostMapping("/contract/{contractId}")
    public ResponseEntity<BreachReport> generate(@PathVariable Long contractId) {
        return ResponseEntity.ok(
                breachReportService.generateReport(contractId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreachReport> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                breachReportService.getReportById(id)
        );
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<BreachReport>> getByContract(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(
                breachReportService.getReportsForContract(contractId)
        );
    }

    @GetMapping
    public ResponseEntity<List<BreachReport>> getAll() {
        return ResponseEntity.ok(
                breachReportService.getAllReports()
        );
    }
}
