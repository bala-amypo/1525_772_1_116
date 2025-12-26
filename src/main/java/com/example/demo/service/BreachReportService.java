package com.example.demo.service;

import com.example.demo.entity.BreachReport;
import com.example.demo.repository.BreachReportRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BreachReportService {

    private BreachReportRepository breachReportRepository;

    public BreachReport saveReport(BreachReport report) {
        return breachReportRepository.save(report);
    }

    public List<BreachReport> getAllReports() {
        return breachReportRepository.findAll();
    }
}
