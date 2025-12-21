package com.example.demo.service;

import com.example.demo.entity.BreachReport;

import java.util.List;

public interface BreachReportService {

    BreachReport generateReport(Long contractId);

    BreachReport getReportById(Long id);

    List<BreachReport> getReportsForContract(Long contractId);

    List<BreachReport> getAllReports();
}
